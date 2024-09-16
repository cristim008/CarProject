package com.HomeworkCar.HomeworkCar.controller.dto;

import com.HomeworkCar.HomeworkCar.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private int quantity;
    private UserDto user;
    private CarDto car;
}
