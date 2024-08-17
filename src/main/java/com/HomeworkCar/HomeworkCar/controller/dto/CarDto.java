package com.HomeworkCar.HomeworkCar.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CarDto {

private String model;

private String type;

private int year;



}
