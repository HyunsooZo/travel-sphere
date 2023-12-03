package com.travelsphere.dto;

import com.travelsphere.domain.User;
import com.travelsphere.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenIssuanceDto {
    private Long id;
    private String email;
    private UserRole userRole;

    public static TokenIssuanceDto from(User user) {
        return TokenIssuanceDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .build();
    }
}
