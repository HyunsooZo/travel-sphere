package com.travelsphere.domain;

import com.travelsphere.dto.UserSignUpRequestDto;
import com.travelsphere.enums.UserRole;
import com.travelsphere.enums.UserStatus;
import lombok.*;

import javax.persistence.*;

import static com.travelsphere.enums.UserRole.ROLE_USER;
import static com.travelsphere.enums.UserStatus.PENDING;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column
    @Setter
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public static User from(UserSignUpRequestDto userSignUpRequestDto,
                            String encodedPassword) {
        return User.builder()
                .email(userSignUpRequestDto.getEmail())
                .password(encodedPassword)
                .phone(userSignUpRequestDto.getPhone())
                .userRole(ROLE_USER)
                .userStatus(PENDING)
                .build();
    }
}
