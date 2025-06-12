package com.gamereview.api.dtos;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private final String tokenType = "Bearer";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
