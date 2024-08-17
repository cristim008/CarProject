package com.HomeworkCar.HomeworkCar.controller;

import com.HomeworkCar.HomeworkCar.controller.dto.CarDto;
import com.HomeworkCar.HomeworkCar.controller.dto.PriceTagDto;
import com.HomeworkCar.HomeworkCar.persistance.dao.Car;
import com.HomeworkCar.HomeworkCar.service.CarService;
import com.HomeworkCar.HomeworkCar.service.PriceTagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final PriceTagService priceTagService;


    public CarController(CarService carService, PriceTagService priceTagService) {
        this.carService = carService;
        this.priceTagService = priceTagService;
    }

    @GetMapping

    public List<CarDto> getCars() {
        return carService.getCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayLoad<CarDto>> getCarById(@PathVariable int id) {
        CarDto carDto = carService.getCarsById(id);
        if (carDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponsePayLoad<>(carDto, "UserDto found"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayLoad<>(null, "UserDto not found"));
        }

    }

    @GetMapping("/{id}/{model}")
    public CarDto getCarByIdAndPrintModel(@PathVariable int id, @PathVariable String model) {
        System.out.println("id :" + id + "," + "model :" + model);
        return carService.getCarsById(id);
    }

    @GetMapping("/car")
    public List<CarDto> getCarsByFilters(@RequestParam(required = false) String model,
                                           @RequestParam(required = false) Integer year,
                                           @RequestParam(required = false) String type){
        return carService.getCarsByFilters(model,type,year);
    }
    @PostMapping

    public ResponseEntity<String> addCar(@RequestBody CarDto carDto) {
        carService.addCar(carDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("CarDto added successfully");

    }

    @PutMapping

    public ResponseEntity<String> updateCar(@RequestParam int id, @RequestBody CarDto carDto) {
        carService.updateAndLoadCarData(carDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("CarDto added successfully");


    }

    @PostMapping("/{carId}/price")
    public ResponseEntity<String> addPriceTag(@PathVariable Long carId, @RequestBody PriceTagDto priceTagDto) {
        priceTagService.addPriceTag(priceTagDto, carId);
        return ResponseEntity.status(HttpStatus.CREATED).body("PriceTag added successfully");
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<String> updatePriceTag(@PathVariable Long id, @RequestBody PriceTagDto priceTagDto) {
        priceTagService.updatePriceTag(priceTagDto, id);
        return ResponseEntity.status(HttpStatus.OK).body("PriceTag updated successfully");
    }


}
