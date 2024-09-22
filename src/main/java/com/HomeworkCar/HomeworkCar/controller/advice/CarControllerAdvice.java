package com.HomeworkCar.HomeworkCar.controller.advice;

import com.HomeworkCar.HomeworkCar.exceptions.CarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CarControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<String> handleCarNotFoundException(CarNotFoundException carNotFoundException){
        return new ResponseEntity<>(carNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
