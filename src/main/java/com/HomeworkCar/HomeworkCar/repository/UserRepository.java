package com.HomeworkCar.HomeworkCar.repository;

import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername (String user);
    boolean existsByEmail (String email);
    Optional<User> findByEmail (String email);

    @Query("SELECT u FROM User u WHERE" +
            "(:username IS NULL OR u.username = :username) AND " +
            "(:email IS NULL OR u.email = :email)")
    List<User> findUsersByFilter(@Param("username") String username,
                                 @Param("email") String email);
}
