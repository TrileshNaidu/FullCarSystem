package com.example.FullCarSystem.Modules.repository;

import com.example.FullCarSystem.Modules.model.Vehicle;
import com.example.FullCarSystem.Modules.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findById(Long id);
    List<Vehicle> findByType(CarType type);
    List<Vehicle> findAllByOrderByIdAsc();
    List<Vehicle> findByMakeIgnoreCase(String make);
    List<Vehicle> findByModelIgnoreCase(String model);
    List<Vehicle> findByOwnerId(Long ownerId);
} 