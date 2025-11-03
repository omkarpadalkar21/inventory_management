package com.adv_java.inventory_management.services;

import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.entities.User;

public interface UserService {
    User registerUser(RegisterRequestDto registerRequestDto);
    User loginUser(String email, String password);
}