package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.WalletDto;
import com.HomeworkCar.HomeworkCar.exceptions.InsuficientFundsException;
import com.HomeworkCar.HomeworkCar.exceptions.WalletAlreadyExistException;
import com.HomeworkCar.HomeworkCar.exceptions.WalletNotFoundException;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.persistance.entities.Wallet;
import com.HomeworkCar.HomeworkCar.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final UserService userService;
    private final WalletRepository walletRepository;

    public WalletService(UserService userService, WalletRepository walletRepository) {
        this.userService = userService;
        this.walletRepository = walletRepository;
    }

    public Wallet createWalletForUserId(Long userId) {
        User user = userService.getUserEntityById(userId);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0);

        return walletRepository.save(wallet);
    }


    public Wallet getWalletForUserId(Long userId) {
        return walletRepository.findById(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for user : " + userId));
    }

    public void addFunds(Long userId, int amount) {
        Wallet wallet = getWalletForUserId(userId);
        wallet.setBalance(wallet.getBalance() + amount);

        walletRepository.save(wallet);

    }

    public void deductFunds(Long userId, int amount) {
        Wallet wallet = getWalletForUserId(userId);
        if (wallet.getBalance() < amount) {
            throw new InsuficientFundsException("Not enough balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);
    }

    public boolean hasSufficeFunds(Long userId, int amount) {
        Wallet wallet = getWalletForUserId(userId);
        return wallet.getBalance() >= amount;
    }


}
