package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.dtos.UserDto;
import com.adv_java.inventory_management.domain.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T19:29:55+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromRegisterDto(RegisterRequestDto registerRequestDto) {
        if ( registerRequestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( registerRequestDto.getUsername() );
        user.email( registerRequestDto.getEmail() );
        user.password( registerRequestDto.getPassword() );
        user.role( registerRequestDto.getRole() );

        return user.build();
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.username( user.getUsername() );
        userDto.email( user.getEmail() );
        if ( user.getRole() != null ) {
            userDto.role( user.getRole().name() );
        }
        userDto.createdAt( user.getCreatedAt() );

        return userDto.build();
    }
}
