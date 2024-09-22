package com.HomeworkCar.HomeworkCar.controller;

import com.HomeworkCar.HomeworkCar.controller.dto.LoginRequestDto;
import com.HomeworkCar.HomeworkCar.controller.dto.UserDto;
import com.HomeworkCar.HomeworkCar.controller.dto.WalletDto;
import com.HomeworkCar.HomeworkCar.exceptions.EmailAlreadyUsedException;
import com.HomeworkCar.HomeworkCar.exceptions.InvalidPasswordException;
import com.HomeworkCar.HomeworkCar.exceptions.UserAlreadyExist;
import com.HomeworkCar.HomeworkCar.exceptions.UserNotFoundException;
import com.HomeworkCar.HomeworkCar.mappers.WalletMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.service.UserService;
import com.HomeworkCar.HomeworkCar.service.WalletService;
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
    private final WalletService walletService;
    private final WalletMapper walletMapper;

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

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(User user) {
        List<UserDto> users = userService.getAllUsers(user);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ResponsePayLoad<UserDto>> getUserById(@PathVariable long id) {
        UserDto userDto = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponsePayLoad<>(userDto, "User has been found"));
    }


    @PostMapping("/{userId}/create-wallet")
    public ResponseEntity<WalletDto> createWallet(@PathVariable Long userId) {
        var wallet = walletService.createWalletForUserId(userId);
        return ResponseEntity.ok(walletMapper.toWalletDto(wallet));
    }

}
