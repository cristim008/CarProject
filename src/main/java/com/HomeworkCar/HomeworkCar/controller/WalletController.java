package com.HomeworkCar.HomeworkCar.controller;

import com.HomeworkCar.HomeworkCar.controller.dto.WalletDto;
import com.HomeworkCar.HomeworkCar.mappers.WalletMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.persistance.entities.Wallet;
import com.HomeworkCar.HomeworkCar.service.UserService;
import com.HomeworkCar.HomeworkCar.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;
    private final WalletMapper walletMapper;

    @PostMapping("/create")
    public ResponseEntity<String> createWallet(@PathVariable Long userId) {
        walletService.createWalletForUserId(userId);
        return ResponseEntity.ok("Wallet created successfully for user ID: " + userId);
    }

    @PostMapping("/addfunds")
    public ResponseEntity<String> addFunds(@RequestParam Long userId, @RequestParam int amount) {
        walletService.addFunds(userId, amount);
        return ResponseEntity.ok("Funds added successfully!");
    }

    @GetMapping("/balance")
    public ResponseEntity<WalletDto> getWallet(@RequestParam Long userId) {
        Wallet wallet = walletService.getWalletForUserId(userId);
        WalletDto walletDto = walletMapper.toWalletDto(wallet);
        return ResponseEntity.ok(walletDto);
    }

}
