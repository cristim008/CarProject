package com.HomeworkCar.HomeworkCar.controller;

import com.HomeworkCar.HomeworkCar.controller.dto.LoginRequestDto;
import com.HomeworkCar.HomeworkCar.controller.dto.UserDto;
import com.HomeworkCar.HomeworkCar.exceptions.EmailAlreadyUsedException;
import com.HomeworkCar.HomeworkCar.exceptions.InvalidPasswordException;
import com.HomeworkCar.HomeworkCar.exceptions.UserAlreadyExist;
import com.HomeworkCar.HomeworkCar.exceptions.UserNotFoundException;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")

    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user was added successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        UserDto loggedInUser = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok("Login successful for user : " + loggedInUser.getUsername());

    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<ResponsePayLoad<UserDto>> getUserById(@PathVariable long id) {
        UserDto userDto = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponsePayLoad<>(userDto, "User has been found"));
    }


}
