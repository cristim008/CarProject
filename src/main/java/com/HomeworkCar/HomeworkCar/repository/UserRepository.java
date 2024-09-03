package com.HomeworkCar.HomeworkCar.repository;

import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE" +
            "(:username IS NULL OR u.username = :username) AND " +
            "(:email IS NULL OR u.email = :email)")
    List<User> findUsersByFilter(@Param("username") String username,
                                 @Param("email") String email);
}
