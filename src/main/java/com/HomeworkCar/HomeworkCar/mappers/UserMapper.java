package com.HomeworkCar.HomeworkCar.mappers;

import com.HomeworkCar.HomeworkCar.controller.dto.UserDto;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDto (User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public User toUserEntity (UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }
}
