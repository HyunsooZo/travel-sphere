package com.travelsphere.dto;

import com.travelsphere.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignInDto {
    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String phone;

    public static UserSignInDto from(User user, String AC , String RC) {
        return UserSignInDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .accessToken(AC)
                .refreshToken(RC)
                .build();
    }
}
