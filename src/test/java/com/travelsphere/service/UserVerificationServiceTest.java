package com.travelsphere.service;

import com.travelsphere.domain.User;
import com.travelsphere.enums.UserRole;
import com.travelsphere.enums.UserStatus;
import com.travelsphere.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("이메일인증 테스트")
class UserVerificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    static User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .phone("01012345678")
            .password("encoded")
            .userStatus(UserStatus.PENDING)
            .userRole(UserRole.ROLE_USER)
            .build();

    static User inactiveUser = User.builder()
            .id(2L)
            .email("test@test.com")
            .phone("01012345678")
            .password("encoded")
            .userStatus(UserStatus.ACTIVE)
            .userRole(UserRole.ROLE_USER)
            .build();

    @Test
    @DisplayName("성공")
    void signUp() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //when
        userService.verifyEmail(user.getId());
        //then
        assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVE);
    }

}