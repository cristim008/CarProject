package com.HomeworkCar.HomeworkCar.repository;

import com.HomeworkCar.HomeworkCar.persistance.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
