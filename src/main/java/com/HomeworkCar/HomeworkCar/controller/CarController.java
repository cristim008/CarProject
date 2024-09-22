package com.HomeworkCar.HomeworkCar.controller;

import com.HomeworkCar.HomeworkCar.controller.dto.CarDto;
import com.HomeworkCar.HomeworkCar.mappers.CarMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.Car;
import com.HomeworkCar.HomeworkCar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;


    @GetMapping

    public List<CarDto> getCars() {
        return carService.getCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayLoad<CarDto>> getCarById(@PathVariable long id) {
        CarDto carDto = carService.getCarById(id);
        if (carDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponsePayLoad<>(carDto, "CarDto found"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayLoad<>(null, "CarDto not found"));
        }

    }

    @GetMapping("/{id}/{model}")
    public CarDto getCarByIdAndPrintModel(@PathVariable int id, @PathVariable String model) {
        System.out.println("id :" + id + "," + "model :" + model);
        return carService.getCarById(id);
    }

    @GetMapping("/car")
    public List<CarDto> getCarsByFilters(@RequestParam(required = false) String model,
                                         @RequestParam(required = false) Integer year,
                                         @RequestParam(required = false) String type) {
        return carService.getCarsByFilters(model, type, year);
    }

    @PostMapping

    public ResponseEntity<String> addCar(@RequestBody CarDto carDto) {
        carService.addCar(carDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("CarDto added successfully");
    }

    @PutMapping

    public ResponseEntity<String> updateCar(@RequestParam int id, @RequestBody CarDto carDto) {
        carService.updateCar(carDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Car added successfully");
    }

    @DeleteMapping

    public ResponseEntity<String> deleteCar(@PathVariable long id) {
        carService.removeCarById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("The car with ID : " + id + "Has been deleted");
    }


}
