package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.RegisterRequestDto;
import com.adv_java.inventory_management.domain.dtos.UserDto;
import com.adv_java.inventory_management.domain.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T21:28:00+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromRegisterDto(RegisterRequestDto registerRequestDto) {
        if ( registerRequestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( registerRequestDto.getEmail() );
        user.password( registerRequestDto.getPassword() );
        user.role( registerRequestDto.getRole() );
        user.username( registerRequestDto.getUsername() );

        return user.build();
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.createdAt( user.getCreatedAt() );
        userDto.email( user.getEmail() );
        userDto.id( user.getId() );
        if ( user.getRole() != null ) {
            userDto.role( user.getRole().name() );
        }
        userDto.username( user.getUsername() );

        return userDto.build();
    }
}
