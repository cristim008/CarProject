package com.HomeworkCar.HomeworkCar.mappers;

import com.HomeworkCar.HomeworkCar.controller.dto.OrderDto;
import com.HomeworkCar.HomeworkCar.mappers.CarMapper;
import com.HomeworkCar.HomeworkCar.mappers.UserMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Car;
import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.service.CarService;
import com.HomeworkCar.HomeworkCar.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final UserService userService;
    private final CarService carService;

    public OrderMapper(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }


    public OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .quantity(order.getQuantity())
                .userId(order.getUser().getId())
                .carId(order.getCar().getId())
                .build();
    }


    public Order toOrderEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        User user = userService.getUserEntityById(orderDto.getUserId());
        Car car = carService.getCarEntityId(orderDto.getCarId());

        return Order.builder()
                .id(orderDto.getId())
                .orderDate(orderDto.getOrderDate())
                .orderStatus(orderDto.getOrderStatus())
                .quantity(orderDto.getQuantity())
                .user(user)
                .car(car)
                .build();
    }
}



