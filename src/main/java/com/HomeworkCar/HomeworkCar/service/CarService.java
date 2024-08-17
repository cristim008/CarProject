package com.HomeworkCar.HomeworkCar.service;

import com.HomeworkCar.HomeworkCar.controller.dto.CarDto;
import com.HomeworkCar.HomeworkCar.persistance.dao.Car;
import com.HomeworkCar.HomeworkCar.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public List<CarDto> getCars() {
        return carRepository.findAll().stream()
                .map(car -> CarDto.builder()
                        .model(car.getModel())
                        .type(car.getType())
                        .year(car.getYear())
                        .build())
                .toList();
    }

    public CarDto getCarsById(long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            return null;
        }

        Car car = optionalCar.get();

        return CarDto.builder()
                .model(car.getModel())
                .type(car.getType())
                .year(car.getYear())
                .build();
    }


        public List<CarDto> getCarsByFilters(String model, String type, Integer year) {
            List<Car> filteredCars = carRepository.findCarsByFilters(model, type, year);
            return filteredCars.stream()
                    .map(car -> CarDto.builder()
                            .model(car.getModel())
                            .type(car.getType())
                            .year(car.getYear())
                            .build())
                    .collect(Collectors.toList());

    }

    public void addCar(CarDto carDto) {
        Car car = new Car();
        car.setModel(carDto.getModel());
        car.setType(carDto.getType());
        car.setYear(carDto.getYear());
        carRepository.save(car);
    }

    public void addCar(CarDto carDto, long id) {
        Car car = new Car();
        car.setId(id);
        car.setModel(carDto.getModel());
        car.setType(carDto.getType());
        car.setYear(carDto.getYear());
        carRepository.save(car);
    }

    public void removeCarById(long id) {
        carRepository.deleteById(id);
    }

    public void updateAndLoadCarData(CarDto newCarDto, long id) {
        Car car = carRepository.findById(id).orElseThrow();
        car.setModel(newCarDto.getModel() != null ? newCarDto.getModel() : car.getModel());
        car.setType(newCarDto.getType() != null ? newCarDto.getType() : car.getType());
        car.setYear(newCarDto.getYear() != 0 ? newCarDto.getYear() : car.getYear());

        carRepository.save(car);
    }


}
