package com.HomeworkCar.HomeworkCar.services;

import com.HomeworkCar.HomeworkCar.controller.dto.CarDto;
import com.HomeworkCar.HomeworkCar.exceptions.CarNotFoundException;
import com.HomeworkCar.HomeworkCar.mappers.CarMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Car;
import com.HomeworkCar.HomeworkCar.repository.CarRepository;
import com.HomeworkCar.HomeworkCar.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    private CarService carService;
    private CarRepository mockedCarRepository;
    private CarMapper mockedCarMapper;

    @BeforeEach
    void setup() {

        mockedCarRepository = mock(CarRepository.class);
        mockedCarMapper = mock(CarMapper.class);
        carService = new CarService(mockedCarRepository, mockedCarMapper);
    }

    @Test
    void getCar() {
        //Given

        Car car = new Car();
        car.setId(1L);
        car.setModel("Audi");
        car.setType("SUV");
        car.setPrice(1500);
        car.setYear(2015);

        List<Car> cars = List.of(car);

        //When

        when(mockedCarRepository.findAll()).thenReturn(cars);

        CarDto carDto = new CarDto(1L,
                "Audi",
                "Suv",
                2015,
                1500);

        List<CarDto> expectedCars = List.of(carDto);
        when(mockedCarRepository.findAll()).thenReturn(cars);
        when(mockedCarMapper.toDto(car)).thenReturn(carDto);
        List<CarDto> allCars = carService.getCars();

        //Then
        assertEquals(expectedCars, allCars);

    }

    @Test
    void getCarsById() {

        //Given
        long id = 1L;
        Car car = new Car();
        car.setModel("Audi");
        car.setType("SUV");
        car.setPrice(1500);
        car.setYear(2015);

        CarDto carDto = new CarDto(1L,
                "Audi",
                "Suv",
                2015,
                1500);

        //When

        when(mockedCarRepository.findById(id)).thenReturn(Optional.of(car));
        when(mockedCarMapper.toDto(car)).thenReturn(carDto);

        CarDto result = carService.getCarById(id);

        //Then
        assertEquals(carDto, result);
        verify(mockedCarRepository).findById(id);
        verify(mockedCarMapper).toDto(car);
    }

    @Test
    void getCarsById_CarDoesNotExist_ReturnNull() {
        //Given

        long carId = 1L;

        //When
        when(mockedCarRepository.findById(carId)).thenReturn(Optional.empty());

        CarDto result = carService.getCarById(carId);

        //Then
        assertNull(result);
        verify(mockedCarRepository).findById(carId);
    }

    @Test
    void addCar() {
        //Given
        CarDto carDto = CarDto.builder()
                .model("Audi")
                .type("Suv")
                .year(2015)
                .price(1500)
                .build();

        Car car = new Car();
        car.setModel("Audi");
        car.setType("SUV");
        car.setPrice(1500);
        car.setYear(2015);

        //When
        when(mockedCarMapper.toEntity(carDto)).thenReturn(car);

        carService.addCar(carDto);

        //Then

        verify(mockedCarMapper).toEntity(carDto);
        verify(mockedCarRepository).save(car);
    }

    @Test
    void removeCar() {
        //Given
        long carId = 1;

        //When
        carService.removeCarById(carId);

        //Then
        verify(mockedCarRepository).deleteById(carId);
    }

    @Test
    void  getPriceById(){
       //Given
        Car car = new Car();
        car.setModel("Audi");
        car.setType("SUV");
        car.setPrice(1500);
        car.setYear(2015);

        //When

        when(mockedCarRepository.findById(car.getId())).thenReturn(Optional.of(car));

        //Then
        int result=carService.getCarsPriceById(car.getId());
        assertEquals(car.getPrice(),result);
        verify(mockedCarRepository).findById(car.getId());
    }

    @Test
    void getPriceById_ThrowCarNotFoundException (){
        //Given
        Car car = new Car();
        car.setModel("Audi");
        car.setType("SUV");
        car.setPrice(1500);
        car.setYear(2015);

        //When
        when(mockedCarRepository.findById(car.getId())).thenReturn(Optional.empty());
        //Then
        assertThrows(CarNotFoundException.class,()->carService.getCarsPriceById(car.getId()));
        verify(mockedCarRepository).findById(car.getId());
    }

}
