package com.HomeworkCar.HomeworkCar.mappers;

import com.HomeworkCar.HomeworkCar.controller.dto.CarDto;
import com.HomeworkCar.HomeworkCar.persistance.entities.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public  CarDto toDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .type(car.getType())
                .year(car.getYear())
                .price(car.getPrice())
                .build();
    }

    public  Car toEntity(CarDto carDto) {
        return Car.builder()
                .id(carDto.getId())
                .model(carDto.getModel())
                .type(carDto.getType())
                .year(carDto.getYear())
                .price(carDto.getPrice())
                .build();
    }
}

