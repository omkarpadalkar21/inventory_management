package com.adv_java.inventory_management.controllers;

import com.adv_java.inventory_management.domain.dtos.AuthResponseDto;
import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.entities.User;
import com.adv_java.inventory_management.mapper.UserMapper;
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

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponseDto> register(
            @Valid @RequestBody RegisterRequestDto registerRequestDto
    ) {
        User user = userService.registerUser(registerRequestDto);
        AuthResponseDto authResponseDto = userMapper.toAuthResponseDto(user);

        return new ResponseEntity(
                authResponseDto,
                HttpStatus.CREATED
        );
    }
}
