package com.HomeworkCar.HomeworkCar.mappers;

import com.HomeworkCar.HomeworkCar.controller.dto.PaymentDto;
import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import com.HomeworkCar.HomeworkCar.persistance.entities.Payment;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.service.OrderService;
import com.HomeworkCar.HomeworkCar.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private final UserService userService;
    private final OrderService orderService;

    public PaymentMapper(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    public PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .userId(payment.getUser().getId())
                .orderId(payment.getOrder().getId())
                .build();
    }

    public Payment toPaymentEntity(PaymentDto paymentDto) {
        User user = userService.getUserEntityById(paymentDto.getUserId());
        Order order = orderService.getOrderEntity(paymentDto.getOrderId());

        return Payment.builder()
                .id(paymentDto.getId())
                .amount(paymentDto.getAmount())
                .paymentMethod(paymentDto.getPaymentMethod())
                .user(user)
                .order(order)
                .isSuccessful(false)
                .build();
    }
}
