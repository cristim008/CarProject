package com.HomeworkCar.HomeworkCar.controller.advice;

import com.HomeworkCar.HomeworkCar.exceptions.InsuficientFundsException;
import com.HomeworkCar.HomeworkCar.exceptions.WalletAlreadyExistException;
import com.HomeworkCar.HomeworkCar.exceptions.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WalletControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<String> handleWalletNotFoundException (WalletNotFoundException walletNotFoundException){
        return new ResponseEntity<>(walletNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler

    public ResponseEntity<String> handleInsuficientFundsException (InsuficientFundsException insuficientFundsException){
        return new ResponseEntity<>(insuficientFundsException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler

    public ResponseEntity<String> handleWalletAlreadyExistException (WalletAlreadyExistException walletAlreadyExistException){
        return new ResponseEntity<>(walletAlreadyExistException.getMessage(),HttpStatus.CONFLICT);
    }
}
