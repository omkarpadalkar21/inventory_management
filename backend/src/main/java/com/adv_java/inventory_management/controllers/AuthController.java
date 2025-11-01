package com.adv_java.inventory_management.controllers;

import com.adv_java.inventory_management.domain.dtos.AuthRequestDto;
import com.adv_java.inventory_management.domain.dtos.AuthResponseDto;
import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.entities.User;
import com.adv_java.inventory_management.mapper.UserMapper;
import com.adv_java.inventory_management.services.AuthService;
import com.adv_java.inventory_management.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponseDto> register(
            @Valid @RequestBody RegisterRequestDto registerRequestDto
    ) {
        User user = userService.registerUser(registerRequestDto);
        String token = authService.generateToken(user.getUsername());
        
        AuthResponseDto authResponseDto = userMapper.toAuthResponseDto(
                user, 
                token, 
                "Registration successful"
        );
        
        return new ResponseEntity<>(
                authResponseDto,
                HttpStatus.CREATED
        );
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody AuthRequestDto authRequestDto
    ) {
        User user = userService.loginUser(authRequestDto.getEmail(), authRequestDto.getPassword());
        String token = authService.generateToken(user.getUsername());
        
        AuthResponseDto authResponseDto = userMapper.toAuthResponseDto(
                user, 
                token, 
                "Login successful"
        );
        
        return ResponseEntity.ok(authResponseDto);
    }
}
