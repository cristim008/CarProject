package com.HomeworkCar.HomeworkCar.mappers;

import com.HomeworkCar.HomeworkCar.controller.dto.OrderDto;
import com.HomeworkCar.HomeworkCar.mappers.CarMapper;
import com.HomeworkCar.HomeworkCar.mappers.UserMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final UserMapper userMapper;
    private final CarMapper carMapper;

    public OrderMapper(UserMapper userMapper, CarMapper carMapper) {
        this.userMapper = userMapper;
        this.carMapper = carMapper;
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
                .user(userMapper.toUserDto(order.getUser()))
                .car(carMapper.toDto(order.getCar()))
                .build();
    }

    public Order toOrderEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        return Order.builder()
                .id(orderDto.getId())
                .orderDate(orderDto.getOrderDate())
                .orderStatus(orderDto.getOrderStatus())
                .quantity(orderDto.getQuantity())
                .user(userMapper.toUserEntity(orderDto.getUser()))
                .car(carMapper.toEntity(orderDto.getCar()))
                .build();
    }
}

