package com.HomeworkCar.HomeworkCar.repository;

import com.HomeworkCar.HomeworkCar.persistance.entities.Payment;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    public List<Payment> findUserById (Long userId);

    public Optional<Payment> findOrderById (Long orderId);
}
