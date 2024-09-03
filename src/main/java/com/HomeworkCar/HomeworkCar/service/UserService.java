package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.UserDto;
import com.HomeworkCar.HomeworkCar.mappers.UserMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.toUserEntity(userDto));
    }

    public Optional<UserDto> getUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(userMapper::toUserDto);
    }

}
