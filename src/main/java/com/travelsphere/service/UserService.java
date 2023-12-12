package com.travelsphere.service;

import com.travelsphere.config.JwtProvider;
import com.travelsphere.domain.User;
import com.travelsphere.dto.*;
import com.travelsphere.enums.UserStatus;
import com.travelsphere.exception.CustomException;
import com.travelsphere.exception.ErrorCode;
import com.travelsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.travelsphere.enums.UserStatus.ACTIVE;
import static com.travelsphere.enums.VerificationText.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate redisTemplate;
    private final String AUTH_PREFIX = "RC : ";

    /**
     * 사용자 회원가입을 처리하는 메서드
     *
     * @param userSignUpRequestDto 사용자 회원가입 요청 DTO
     * @return 회원가입된 사용자 정보를 담은 UserDto
     * @throws CustomException 이미 존재하는 사용자인 경우 발생하는 예외
     */
    @Transactional
    public UserDto signUp(UserSignUpRequestDto userSignUpRequestDto) {

        // 이메일 중복 확인
        userRepository.findByEmail(userSignUpRequestDto.getEmail())
                .ifPresent(user -> {
                    throw new CustomException(ErrorCode.EXISTING_USER);
                });

        // 비밀번호 암호화
        String encodedPassword =
                passwordEncoder.encode(userSignUpRequestDto.getPassword());

        // 사용자 저장
        User user = userRepository.save(
                User.from(userSignUpRequestDto, encodedPassword)
        );

        return UserDto.from(user);
    }

    /**
     * 사용자의 이메일을 인증하는 메서드
     *
     * @param userId 사용자의 고유 식별자
     * @return 이메일 인증 완료 메시지
     * @throws CustomException 사용자 정보를 찾을 수 없을 때 발생하는 예외
     */
    @Transactional
    public String verifyEmail(Long userId) {
        User user = getUser(userId, null);
        UserStatus userStatus = user.getUserStatus();

        if (userStatus.equals(ACTIVE)) {
            return ALREADY_VERIFIED;
        }

        if (userStatus.equals(UserStatus.INACTIVE)) {
            return DELETED_USER;
        }

        user.setUserStatus(ACTIVE);

        return VERIFIED;
    }

    /**
     * 사용자 정보를 조회하는 내부 메서드
     *
     * @param userId 사용자의 고유 식별자
     * @return 주어진 식별자에 해당하는 사용자 정보
     * @throws CustomException 사용자 정보를 찾을 수 없을 때 발생하는 예외
     */
    private User getUser(Long userId, String email) {
        if (userId != null) {
            return userRepository.findById(userId).orElseThrow(
                    () -> new CustomException(ErrorCode.USER_INFO_NOT_FOUND)
            );
        } else if (email != null) {
            return userRepository.findByEmail(email).orElseThrow(
                    () -> new CustomException(ErrorCode.USER_INFO_NOT_FOUND)
            );
        } else {
            throw new CustomException(ErrorCode.USER_INFO_NOT_FOUND);
        }
    }

    /**
     * 사용자 로그인을 처리하는 메서드.
     *
     * @param userSignInRequestDto 사용자 로그인 요청 정보를 담은 DTO 객체
     * @return 사용자 로그인 정보를 담은 DTO 객체
     * @throws CustomException 로그인에 실패했을 때 발생하는 예외 처리
     */
    @Transactional(readOnly = true)
    public UserSignInDto signInUser(UserSignInRequestDto userSignInRequestDto) {
        User user = getUser(null, userSignInRequestDto.getEmail());

        if (!passwordEncoder.matches(userSignInRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        if (!user.getUserStatus().equals(UserStatus.ACTIVE)) {
            throw new CustomException(ErrorCode.USER_STATUS_NOT_ACTIVE);
        }

        String accessToken = jwtProvider.issueAccessToken(TokenIssuanceDto.from(user));
        String refreshToken = jwtProvider.issueRefreshToken();

        redisTemplate.opsForValue().set(
                AUTH_PREFIX + user.getEmail(), refreshToken
        );

        redisTemplate.expire(
                AUTH_PREFIX + user.getEmail(), 7, TimeUnit.DAYS
        );

        return UserSignInDto.from(user, accessToken, refreshToken);
    }
}
