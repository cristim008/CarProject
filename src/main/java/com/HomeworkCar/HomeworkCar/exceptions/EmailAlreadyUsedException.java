package com.HomeworkCar.HomeworkCar.exceptions;

public class EmailAlreadyUsedException extends RuntimeException{

    public EmailAlreadyUsedException (String message){
        super(message);
    }
}
