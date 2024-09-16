package com.HomeworkCar.HomeworkCar.controller.advice;

import com.HomeworkCar.HomeworkCar.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<String> handleOrderNotFoundException (OrderNotFoundException orderNotFoundException){
        return new ResponseEntity<>(orderNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
