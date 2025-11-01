package com.adv_java.inventory_management.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails validateToken(String token);
}
