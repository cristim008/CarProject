package com.HomeworkCar.HomeworkCar.persistance.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="cars")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Model must be only letters")
    private String model;

    @NotNull
    private String type;

    @NotNull
    @Positive
    private int year;

    @NotNull
    @Positive
    private  int price;

    @OneToMany(mappedBy = "car")
    private List<Order> orders;



}
