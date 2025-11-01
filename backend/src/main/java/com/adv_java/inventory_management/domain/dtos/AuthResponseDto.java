package com.adv_java.inventory_management.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {
    private String token;           // JWT authentication token
    private UserDto user;           // User information
    private String message;         // Optional success message
}