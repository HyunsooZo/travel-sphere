package com.travelsphere.service;

import com.travelsphere.domain.User;
import com.travelsphere.dto.UserDto;
import com.travelsphere.dto.UserSignUpRequestDto;
import com.travelsphere.exception.CustomException;
import com.travelsphere.exception.ErrorCode;
import com.travelsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
