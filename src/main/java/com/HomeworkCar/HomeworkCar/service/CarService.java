package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.CarDto;
import com.HomeworkCar.HomeworkCar.exceptions.CarNotFoundException;
import com.HomeworkCar.HomeworkCar.mappers.CarMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Car;
import com.HomeworkCar.HomeworkCar.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public List<CarDto> getCars() {
        return carRepository.findAll().stream()
                .map(carMapper::toDto) // Folosește CarMapper pentru conversie
                .collect(Collectors.toList());
    }

    public CarDto getCarById(long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(carMapper::toDto).orElse(null);
    }

    public List<CarDto> getCarsByFilters(String model, String type, Integer year) {
        List<Car> filteredCars = carRepository.findCarsByFilters(model, type, year);
        return filteredCars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());

    }

    public void addCar(CarDto carDto) {
        carRepository.save(carMapper.toEntity(carDto));
    }

    public void updateCar(CarDto carDto, long id) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found "));

        if (carDto.getModel() != null) {
            existingCar.setModel(carDto.getModel());
        }
        if (carDto.getType() != null) {
            existingCar.setType(carDto.getType());
        }
        if (carDto.getYear() != 0) {
            existingCar.setYear(carDto.getYear());
        }
        if (carDto.getPrice() != 0) {
            existingCar.setPrice(carDto.getPrice());
        }

        carRepository.save(existingCar);
    }

    public void removeCarById(long id) {
        carRepository.deleteById(id);
    }

    public Car getCarEntityId(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id :" + carId));
    }

    public int getCarsPriceById(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found with ID: " + carId));
        return car.getPrice();
    }
}

