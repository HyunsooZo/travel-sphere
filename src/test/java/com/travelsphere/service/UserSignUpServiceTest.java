package com.travelsphere.service;

import com.travelsphere.config.JwtProvider;
import com.travelsphere.domain.User;
import com.travelsphere.dto.UserDto;
import com.travelsphere.dto.UserSignUpRequestDto;
import com.travelsphere.enums.UserRole;
import com.travelsphere.enums.UserStatus;
import com.travelsphere.exception.CustomException;
import com.travelsphere.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.travelsphere.exception.ErrorCode.EXISTING_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DisplayName("회원가입 테스트")
class UserSignUpServiceTest {

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

    static UserSignUpRequestDto userSignUpRequestDto = UserSignUpRequestDto.builder()
            .email("test@test.com")
            .phone("01012345678")
            .password("test1234")
            .build();

    static User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .phone("01012345678")
            .password("encoded")
            .userStatus(UserStatus.PENDING)
            .userRole(UserRole.ROLE_USER)
            .build();

    @Test
    @DisplayName("성공")
    void signUp() {
        //given
        when(userRepository.findByEmail(userSignUpRequestDto.getEmail()))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                .thenReturn("encoded");
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        //when
        UserDto userDto = userService.signUp(userSignUpRequestDto);
        //then
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getPhone()).isEqualTo(user.getPhone());
    }

    @Test
    @DisplayName("실패 - 이미 존재하는 사용자")
    void signUp_fail_existing_user() {
        //given
        when(userRepository.findByEmail(userSignUpRequestDto.getEmail()))
                .thenReturn(Optional.of(user));
        //when&then
        Assertions.assertThatThrownBy(() -> userService.signUp(userSignUpRequestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(EXISTING_USER.getMessage());
    }
}