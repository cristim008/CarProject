package com.HomeworkCar.HomeworkCar.controller.advice;

import com.HomeworkCar.HomeworkCar.exceptions.PaymentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handlePaymentNotFoundException (PaymentNotFoundException paymentNotFoundException){
        return new ResponseEntity<>(paymentNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
