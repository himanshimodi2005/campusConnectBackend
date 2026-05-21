package com.example.campusconnect.DTO;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String role;
}