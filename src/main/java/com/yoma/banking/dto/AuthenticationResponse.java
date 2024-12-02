package com.yoma.banking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String access_token;
    private String refresh_token;
}
