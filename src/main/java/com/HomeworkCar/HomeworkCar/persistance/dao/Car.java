package com.HomeworkCar.HomeworkCar.persistance.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name="cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Model must be only letters")
    private String model;

    @NotNull
    private String type;

    @NotNull
    private int year;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private PriceTag priceTag;


}
