package com.travelsphere.dto;

import com.travelsphere.domain.User;
import com.travelsphere.enums.UserRole;
import com.travelsphere.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String phone;
    private UserRole userRole;
    private UserStatus userStatus;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .build();
    }
}
