package com.example.FullCarSystem.Modules.repository;

import com.example.FullCarSystem.Modules.model.RentalVehicle;
import com.example.FullCarSystem.Modules.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalVehicleRepository extends JpaRepository<RentalVehicle, Long> {
    Optional<RentalVehicle> findById(Long id);
    List<RentalVehicle> findByTypeAndAvailable(CarType type, boolean available);
    List<RentalVehicle> findByStatus(com.example.FullCarSystem.Modules.model.RentalVehicle.Status status);
    List<RentalVehicle> findByOwnerId(Long ownerId);
} 