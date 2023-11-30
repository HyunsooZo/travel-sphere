package com.travelsphere.dto;

import com.travelsphere.enums.UserStatus.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenIssuanceDto {
    private Long id;
    private String email;
    private UserRole userRole;
}
