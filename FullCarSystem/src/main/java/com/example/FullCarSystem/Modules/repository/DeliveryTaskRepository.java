package com.example.FullCarSystem.Modules.repository;

import com.example.FullCarSystem.Modules.model.DeliveryTask;
import com.example.FullCarSystem.Modules.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryTaskRepository extends JpaRepository<DeliveryTask, Long> {
    Optional<DeliveryTask> findById(Long id);
    List<DeliveryTask> findByBooking_RentalVehicle_Type(CarType type);
    List<DeliveryTask> findByDeliveryStatus(DeliveryTask.DeliveryStatus status);
    List<DeliveryTask> findByBooking_Id(Long bookingId);
} 