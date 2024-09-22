package com.HomeworkCar.HomeworkCar.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;
    private int amount;
    private String paymentMethod;
    private Long userId;
    private Long orderId;
}
