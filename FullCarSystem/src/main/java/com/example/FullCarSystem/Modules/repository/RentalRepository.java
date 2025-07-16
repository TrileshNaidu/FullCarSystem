package com.example.FullCarSystem.Modules.repository;


import com.example.FullCarSystem.Modules.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCarOwnerId(Long userId);
    List<Rental> findByRenterId(Long userId);
}