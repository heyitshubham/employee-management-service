package com.cnh.tlrevenuerecognition.payload.response;

import lombok.*;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {
    private String userName;
    private String password;
}
