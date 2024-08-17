package com.HomeworkCar.HomeworkCar.repository;

import com.HomeworkCar.HomeworkCar.persistance.dao.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE " +
            "(:model IS NULL OR c.model = :model) AND " +
            "(:type IS NULL OR c.type = :type) AND " +
            "(:year IS NULL OR c.year = :year)")
    List<Car> findCarsByFilters(@Param("model") String model,
                                @Param("type") String type,
                                @Param("year") Integer year);
}
