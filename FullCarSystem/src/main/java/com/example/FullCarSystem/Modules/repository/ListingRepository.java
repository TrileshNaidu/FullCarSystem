package com.example.FullCarSystem.Modules.repository;

import com.example.FullCarSystem.Modules.model.Listing;
import com.example.FullCarSystem.Modules.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    Optional<Listing> findById(Long id);
    List<Listing> findByListingType(String listingType);
} 