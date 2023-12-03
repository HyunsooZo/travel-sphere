package com.travelsphere.service;

import com.travelsphere.config.JwtProvider;
import com.travelsphere.domain.User;
import com.travelsphere.dto.UserSignInDto;
import com.travelsphere.dto.UserSignInRequestDto;
import com.travelsphere.dto.UserSignInResponseDto;
import com.travelsphere.enums.UserRole;
import com.travelsphere.enums.UserStatus;
import com.travelsphere.exception.CustomException;
import com.travelsphere.exception.ErrorCode;
import com.travelsphere.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("로그인 테스트")
class UserSignInServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private StringRedisTemplate redisTemplate;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(
                userRepository, passwordEncoder, jwtProvider, redisTemplate
        );
    }

    static User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .phone("01012345678")
            .password("encoded")
            .userStatus(UserStatus.ACTIVE)
            .userRole(UserRole.ROLE_USER)
            .build();

    @Test
    @DisplayName("성공")
    void signIn() {
        //given
        UserSignInRequestDto userSignInRequestDto = UserSignInRequestDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        UserSignInResponseDto userSignInResponseDto = UserSignInResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .accessToken("AC")
                .refreshToken("RC")
                .phone(user.getPhone())
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtProvider.issueAccessToken(Mockito.any()))
                .thenReturn(userSignInResponseDto.getAccessToken());
        when(jwtProvider.issueRefreshToken())
                .thenReturn(userSignInResponseDto.getRefreshToken());
        when(redisTemplate.opsForValue()).thenReturn(mock(ValueOperations.class));
        when(redisTemplate.expire("RC : " + user.getEmail(), 7, TimeUnit.DAYS))
                .thenReturn(true);

        //when
        UserSignInDto userSignInDto = userService.signInUser(userSignInRequestDto);
        //then
        assertThat(userSignInDto.getPhone()).isEqualTo(userSignInResponseDto.getPhone());
        assertThat(userSignInDto.getEmail()).isEqualTo(userSignInResponseDto.getEmail());
        assertThat(userSignInDto.getAccessToken()).isEqualTo("AC");
        assertThat(userSignInDto.getRefreshToken()).isEqualTo("RC");
    }

    @DisplayName("실패")
    @Test
    void signInFail() {
        //given
        UserSignInRequestDto userSignInRequestDto = UserSignInRequestDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(false);

        //when&then
        Assertions.assertThatThrownBy(() -> userService.signInUser(userSignInRequestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.WRONG_PASSWORD.getMessage());
    }
}