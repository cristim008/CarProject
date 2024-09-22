package com.HomeworkCar.HomeworkCar.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class WalletDto {


    private Long id;
    private int balance;
    private Long userId;
}
