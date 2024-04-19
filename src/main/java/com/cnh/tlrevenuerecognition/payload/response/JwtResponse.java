package com.cnh.tlrevenuerecognition.payload.response;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String username;
    private String role;
}

