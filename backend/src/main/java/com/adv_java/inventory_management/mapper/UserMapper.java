package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.AuthResponseDto;
import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User fromRegisterDto(RegisterRequestDto registerRequestDto);

    AuthResponseDto toAuthResponseDto(User user);
}
