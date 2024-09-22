package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.OrderDto;
import com.HomeworkCar.HomeworkCar.controller.dto.PaymentDto;
import com.HomeworkCar.HomeworkCar.exceptions.InsuficientFundsException;
import com.HomeworkCar.HomeworkCar.exceptions.PaymentNotFoundException;
import com.HomeworkCar.HomeworkCar.mappers.PaymentMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import com.HomeworkCar.HomeworkCar.persistance.entities.Payment;
import com.HomeworkCar.HomeworkCar.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final WalletService walletService;
    private final PaymentMapper paymentMapper;
    private final OrderService orderService;
    private final CarService carService;

    public PaymentService(PaymentRepository paymentRepository, WalletService walletService, PaymentMapper paymentMapper, OrderService orderService, CarService carService) {
        this.paymentRepository = paymentRepository;
        this.walletService = walletService;
        this.paymentMapper = paymentMapper;
        this.orderService = orderService;
        this.carService = carService;
    }

    public PaymentDto processPayment(Long userId, Long orderId, String paymentMethod) {

        Order order=orderService.getOrderEntity(orderId);
        int carPrice= carService.getCarsPriceById(order.getCar().getId());
        int totalAmount=carPrice*order.getQuantity();
        if (!walletService.hasSufficeFunds(userId, totalAmount)) {
            throw new InsuficientFundsException("Insufficient funds for this payment");
        }
        walletService.deductFunds(userId, totalAmount);

        PaymentDto paymentDto = PaymentDto.builder()
                .userId(userId)
                .orderId(orderId)
                .amount(totalAmount)
                .paymentMethod(paymentMethod)
                .build();

        Payment payment = paymentMapper.toPaymentEntity(paymentDto);
        payment.setSuccessful(true);
        paymentRepository.save(payment);

        return paymentMapper.toPaymentDto(payment);
    }

    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + paymentId));
        return paymentMapper.toPaymentDto(payment);
    }

    public List<PaymentDto> getPaymentsForUser(Long userId) {
        List<Payment> payments = paymentRepository.findUserById(userId);
        return payments.stream().map(paymentMapper::toPaymentDto).collect(Collectors.toList());
    }

    public boolean isPaymentSuccessful(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + paymentId));
        return payment.isSuccessful();
    }

    public void cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + paymentId));

        walletService.addFunds(payment.getUser().getId(), payment.getAmount());

        payment.setSuccessful(false);
        paymentRepository.save(payment);
    }

    public PaymentDto getPaymentForOrder(Long orderId) {
        Payment payment = paymentRepository.findOrderById(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("No payment found for order with ID: " + orderId));
        return paymentMapper.toPaymentDto(payment);
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.toPaymentEntity(paymentDto);
        payment = paymentRepository.save(payment);
        return paymentMapper.toPaymentDto(payment);
    }
}

