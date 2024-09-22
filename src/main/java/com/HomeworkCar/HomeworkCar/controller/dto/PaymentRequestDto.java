package com.HomeworkCar.HomeworkCar.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentRequestDto {
    private Long userId;
    private Long orderId;
    private int amount;
    private String paymentMethod;
}
