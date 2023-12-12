package com.travelsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignInResponseDto {
    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String phone;

    public static UserSignInResponseDto from(UserSignInDto userSignInDto) {
        return UserSignInResponseDto.builder()
                .email(userSignInDto.getEmail())
                .accessToken(userSignInDto.getAccessToken())
                .refreshToken(userSignInDto.getRefreshToken())
                .phone(userSignInDto.getPhone())
                .build();
    }
}
