package com.HomeworkCar.HomeworkCar.controller;

import com.HomeworkCar.HomeworkCar.controller.dto.PaymentDto;
import com.HomeworkCar.HomeworkCar.controller.dto.PaymentRequestDto;
import com.HomeworkCar.HomeworkCar.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/process")
    public ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentDto paymentDto = paymentService.processPayment(
                paymentRequestDto.getUserId(),
                paymentRequestDto.getOrderId(),
                paymentRequestDto.getPaymentMethod()
        );
        return ResponseEntity.ok(paymentDto);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsForUser(@PathVariable Long userId) {
        List<PaymentDto> payments = paymentService.getPaymentsForUser(userId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{paymentId}/success")
    public ResponseEntity<Boolean> isPaymentSuccessful(@PathVariable Long paymentId) {
        boolean isSuccessful = paymentService.isPaymentSuccessful(paymentId);
        return ResponseEntity.ok(isSuccessful);
    }

    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentDto> getPaymentForOrder(@PathVariable Long orderId) {
        PaymentDto paymentDto = paymentService.getPaymentForOrder(orderId);
        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        return ResponseEntity.ok(createdPayment);
    }
}
