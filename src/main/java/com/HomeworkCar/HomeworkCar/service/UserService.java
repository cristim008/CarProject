package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.LoginRequestDto;
import com.HomeworkCar.HomeworkCar.controller.dto.UserDto;
import com.HomeworkCar.HomeworkCar.exceptions.EmailAlreadyUsedException;
import com.HomeworkCar.HomeworkCar.exceptions.InvalidPasswordException;
import com.HomeworkCar.HomeworkCar.exceptions.UserAlreadyExist;
import com.HomeworkCar.HomeworkCar.exceptions.UserNotFoundException;
import com.HomeworkCar.HomeworkCar.mappers.UserMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public void registerUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExist("User already exist ");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyUsedException("Email already exist");
        }

        User user = userMapper.toUserEntity(userDto);
        userRepository.save(user);
    }

    public UserDto loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequestDto.getEmail()));

        if (user.getPassword().equals(loginRequestDto.getPassword())) {
            return userMapper.toUserDto(user);
        } else {
            throw new InvalidPasswordException("Invalid password");
        }
    }



    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    public UserDto getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        return userMapper.toUserDto(user);
    }



}
