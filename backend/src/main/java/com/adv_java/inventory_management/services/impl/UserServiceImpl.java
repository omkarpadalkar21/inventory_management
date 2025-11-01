package com.adv_java.inventory_management.services.impl;

import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.entities.User;
import com.adv_java.inventory_management.exception.UserAlreadyExistsException;
import com.adv_java.inventory_management.repository.UserRepository;
import com.adv_java.inventory_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(RegisterRequestDto registerRequestDto) {
        // check if the user already exists
        if (userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User newUser = User.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(registerRequestDto.getRole())
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public User loginUser(String email, String password) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return user;
    }
}
