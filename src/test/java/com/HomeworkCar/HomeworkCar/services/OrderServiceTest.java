package com.HomeworkCar.HomeworkCar.services;

import com.HomeworkCar.HomeworkCar.controller.dto.OrderDto;
import com.HomeworkCar.HomeworkCar.enums.OrderStatus;
import com.HomeworkCar.HomeworkCar.mappers.CarMapper;
import com.HomeworkCar.HomeworkCar.mappers.OrderMapper;
import com.HomeworkCar.HomeworkCar.mappers.UserMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Car;
import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.repository.OrderRepository;
import com.HomeworkCar.HomeworkCar.service.CarService;
import com.HomeworkCar.HomeworkCar.service.OrderService;
import com.HomeworkCar.HomeworkCar.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private  OrderMapper mockedOrderMapper;
    private  OrderRepository mockedOrderRepository;
    private UserMapper mockedUserMapper;
    private CarMapper mockedCarMapper;
    private OrderService orderService;
    private UserService mockedUserService;
    private CarService mockedCarService;

    @BeforeEach
    void setup(){
        mockedCarMapper=mock(CarMapper.class);
        mockedOrderMapper=mock(OrderMapper.class);
        mockedOrderRepository=mock(OrderRepository.class);
        mockedUserMapper=mock(UserMapper.class);
        mockedCarService=mock(CarService.class);
        mockedUserService=mock(UserService.class);
        orderService=new OrderService(mockedOrderMapper,mockedOrderRepository,mockedUserService,mockedCarService);
    }

    @Test
    void createOrder() {
        // Given
        LocalDateTime localDateTime=LocalDateTime.now();
        OrderDto orderDto =OrderDto.builder()
                .id(1l)
                .orderDate(localDateTime)
                .orderStatus(OrderStatus.PENDING)
                .quantity(1)
                .userId(1L)
                .carId(1L)
                .build();
        User user = new User();
        user.setId(1L);
        user.setUsername("Cristi");
        user.setEmail("cristi123@gmail.com");
        user.setPassword("password123");

        Car car = new Car();
        car.setId(1L);
        car.setModel("Audi");
        car.setType("SUV");
        car.setPrice(1500);
        car.setYear(2015);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setCar(car);
        order.setOrderStatus(OrderStatus.PENDING);

        // When
        when(mockedUserService.getUserEntityById(orderDto.getUserId())).thenReturn(user);
        when(mockedCarService.getCarEntityId(orderDto.getCarId())).thenReturn(car);
        when(mockedOrderMapper.toOrderEntity(orderDto)).thenReturn(order);
        when(mockedOrderRepository.save(order)).thenReturn(order);
        when(mockedOrderMapper.toOrderDto(order)).thenReturn(orderDto);

        // Then
        OrderDto result = orderService.createOrder(orderDto);
        assertEquals(orderDto, result);
        verify(mockedOrderRepository).save(order);
    }

    @Test
    void updateOrderStatus(){
        //Given
       LocalDateTime localDateTime=LocalDateTime.now();
        long orderId=1L;
        OrderStatus newStatus=OrderStatus.SHIPPED;
        Order order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.PENDING);
        Order updateOrder=new Order();
        updateOrder.setId(orderId);
        updateOrder.setOrderStatus(newStatus);

        OrderDto updatedOrderDto =new OrderDto(orderId,localDateTime,newStatus,0,1L,1L);

        //When

        when(mockedOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(mockedOrderRepository.save(order)).thenReturn(updateOrder);
        when(mockedOrderMapper.toOrderDto(updateOrder)).thenReturn(updatedOrderDto);

        //Then

        OrderDto result = orderService.updateOrderStatus(orderId, newStatus);
        assertEquals(newStatus, result.getOrderStatus());
        verify(mockedOrderRepository).save(order);
    }

    @Test
    void getOrderById() {
        // Given
        LocalDateTime localDateTime=LocalDateTime.now();
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        OrderDto orderDto = new OrderDto(orderId, localDateTime,OrderStatus.PENDING,1,1L,1L);

        // When
        when(mockedOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(mockedOrderMapper.toOrderDto(order)).thenReturn(orderDto);

        // Then
        OrderDto result = orderService.getOrderById(orderId);
        assertEquals(orderDto, result);
        verify(mockedOrderRepository).findById(orderId);
    }

    @Test
    void getAllOrders() {
        // Given
        LocalDateTime localDateTime=LocalDateTime.now();
        Order order=new Order();
        order.setId(1L);
        order.setOrderDate(localDateTime);
        order.setQuantity(1);
        order.setOrderStatus(OrderStatus.PENDING);

        List<Order> orders = List.of(order);
        List<OrderDto> orderDtos = List.of(new OrderDto(1L, localDateTime, OrderStatus.PENDING,1,1L,1L));

        // When
        when(mockedOrderRepository.findAll()).thenReturn(orders);
        when(mockedOrderMapper.toOrderDto(orders.getFirst())).thenReturn(orderDtos.getFirst());

        // Then
        List<OrderDto> result = orderService.getAllOrders();
        assertEquals(orderDtos.size(), result.size());
        assertEquals(orderDtos.getFirst().getId(), result.getFirst().getId());
        verify(mockedOrderRepository).findAll();
    }

    @Test
    void deleteOrder() {
        // Given
        long orderId = 1L;

        // When
        doNothing().when(mockedOrderRepository).deleteById(orderId);

        // Then
        orderService.deleteOrder(orderId);
        verify(mockedOrderRepository).deleteById(orderId);
    }
    
}
