package com.HomeworkCar.HomeworkCar.persistance.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "wallet")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull
    private int balance;


    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id",unique = true)
    private User user;
}
