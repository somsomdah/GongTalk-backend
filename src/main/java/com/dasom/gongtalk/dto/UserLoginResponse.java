package com.dasom.gongtalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginResponse {
    private String refreshToken;
    private String accessToken;
}
