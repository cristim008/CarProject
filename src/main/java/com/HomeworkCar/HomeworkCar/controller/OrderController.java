package com.HomeworkCar.HomeworkCar.controller;

import com.HomeworkCar.HomeworkCar.controller.dto.OrderDto;
import com.HomeworkCar.HomeworkCar.enums.OrderStatus;
import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.service.CarService;
import com.HomeworkCar.HomeworkCar.service.OrderService;
import com.HomeworkCar.HomeworkCar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CarService carService;
    private final UserService userService;


    @GetMapping

    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();

    }

    @GetMapping("/order/{id}")

    public ResponseEntity<ResponsePayLoad<OrderDto>> getOrderByIdAndShowStatus (@PathVariable long id){
        OrderDto orderDto=orderService.getOrderById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponsePayLoad<>(orderDto,"The order with :" + id + " has status : " +orderDto.getOrderStatus()));
    }


    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status){
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")

    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Order with id : " + orderId + " Has been deleted");
    }

}
