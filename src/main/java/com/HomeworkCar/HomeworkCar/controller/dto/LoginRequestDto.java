package com.HomeworkCar.HomeworkCar.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {

    private String email;

    private String password;
}
