package com.adv_java.inventory_management.services;

import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.entities.User;

public interface UserService {
    public User registerUser(RegisterRequestDto registerRequestDto);
}