package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.OrderDto;
import com.HomeworkCar.HomeworkCar.enums.OrderStatus;
import com.HomeworkCar.HomeworkCar.exceptions.OrderNotFoundException;
import com.HomeworkCar.HomeworkCar.exceptions.UserNotFoundException;
import com.HomeworkCar.HomeworkCar.mappers.OrderMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Car;
import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CarService carService;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, UserService userService, CarService carService) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.carService = carService;
    }

    public OrderDto createOrder(OrderDto orderDto) {
        User user = userService.getUserEntityById(orderDto.getUserId());
        Car car = carService.getCarEntityId(orderDto.getCarId());
        Order order = orderMapper.toOrderEntity(orderDto);
        order.setUser(user);
        order.setCar(car);
        order.setOrderStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDto(savedOrder);
    }

    public OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found "));

        order.setOrderStatus(newStatus);
        Order updateOrder = orderRepository.save(order);
        return orderMapper.toOrderDto(updateOrder);
    }



    public OrderDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not Found"));

        return orderMapper.toOrderDto(order);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    public void  deleteOrder(long id){
        orderRepository.deleteById(id);
    }

    public Order getOrderEntity(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with Id: " + orderId));
    }


}
