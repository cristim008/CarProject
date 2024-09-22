package com.HomeworkCar.HomeworkCar.mappers;

import com.HomeworkCar.HomeworkCar.controller.dto.WalletDto;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.persistance.entities.Wallet;
import com.HomeworkCar.HomeworkCar.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    private final UserService userService;

    public WalletMapper(UserService userService) {
        this.userService = userService;
    }


    public WalletDto toWalletDto (Wallet wallet){
        return WalletDto.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .userId(wallet.getUser().getId())
                .build();
    }

    public Wallet toWalletEntity (WalletDto walletDto){

        User user= userService.getUserEntityById(walletDto.getUserId());
        return Wallet.builder()
                .id(walletDto.getId())
                .balance(walletDto.getBalance())
                .user(user)
                .build();
    }
}
