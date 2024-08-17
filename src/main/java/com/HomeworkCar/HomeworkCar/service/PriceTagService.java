package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.PriceTagDto;
import com.HomeworkCar.HomeworkCar.persistance.dao.Car;
import com.HomeworkCar.HomeworkCar.persistance.dao.PriceTag;
import com.HomeworkCar.HomeworkCar.repository.CarRepository;
import com.HomeworkCar.HomeworkCar.repository.PriceTagRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceTagService {
    private final PriceTagRepository priceTagRepository;
    private final CarRepository carRepository;

    public PriceTagService(PriceTagRepository priceTagRepository, CarRepository carRepository) {
        this.priceTagRepository = priceTagRepository;
        this.carRepository = carRepository;
    }

    public void addPriceTag(PriceTagDto priceTagDto, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        PriceTag priceTag = PriceTag.builder()
                .price(priceTagDto.getPrice())
                .car(car)
                .build();
        car.setPriceTag(priceTag);
        priceTagRepository.save(priceTag);
        carRepository.save(car);
    }

    public void updatePriceTag(PriceTagDto priceTagDto, Long id) {
        PriceTag priceTag = priceTagRepository.findById(id).orElseThrow(() -> new RuntimeException("PriceTag not found"));
        priceTag.setPrice(priceTagDto.getPrice());
        priceTagRepository.save(priceTag);
    }


}

