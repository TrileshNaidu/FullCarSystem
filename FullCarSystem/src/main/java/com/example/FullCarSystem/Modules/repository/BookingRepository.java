package com.example.FullCarSystem.Modules.repository;

import com.example.FullCarSystem.Modules.model.Booking;
import com.example.FullCarSystem.Modules.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);
    List<Booking> findByRentalVehicle_Type(CarType type);
    List<Booking> findByRenterId(Long renterId);
    List<Booking> findByRentalVehicle_IdAndEndDateGreaterThanEqualAndStartDateLessThanEqual(Long rentalVehicleId, java.time.LocalDate startDate, java.time.LocalDate endDate);
} 