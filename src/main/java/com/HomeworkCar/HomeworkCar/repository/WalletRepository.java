package com.HomeworkCar.HomeworkCar.repository;

import com.HomeworkCar.HomeworkCar.persistance.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    boolean existsByUserId  (Long userId);
}
