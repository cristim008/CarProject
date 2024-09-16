package com.HomeworkCar.HomeworkCar.controller.advice;

import com.HomeworkCar.HomeworkCar.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler

    public ResponseEntity<String> handlerUserNotFoundException(UserNotFoundException userNotFoundEx) {
        return new ResponseEntity<>(userNotFoundEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEmailAlreadyUsedException(EmailAlreadyUsedException emailAlreadyUsedEx) {

        return new ResponseEntity<>(emailAlreadyUsedEx.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler

    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExist userAlreadyEx) {
        return new ResponseEntity<>(userAlreadyEx.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler

    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException) {
        return new ResponseEntity<>(invalidPasswordException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
