package com.example.FullCarSystem.Modules.Service;

import com.example.FullCarSystem.Modules.model.Listing;
import com.example.FullCarSystem.Modules.model.Vehicle;
import com.example.FullCarSystem.Modules.DTO.ListingDTO;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import com.example.FullCarSystem.Modules.model.Rating;
import com.example.FullCarSystem.Modules.repository.ListingRepository;
import com.example.FullCarSystem.Modules.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<ListingDTO> getAllListings() {
        return listingRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ListingDTO getListingById(Long id) {
        Optional<Listing> listing = listingRepository.findById(id);
        return listing.map(this::toDTO).orElse(null);
    }

    public ListingDTO createListing(ListingDTO dto) {
        Listing listing = toEntity(dto);
        Listing saved = listingRepository.save(listing);
        return toDTO(saved);
    }

    public ListingDTO updateListing(Long id, ListingDTO dto) {
        Optional<Listing> optional = listingRepository.findById(id);
        if (optional.isPresent()) {
            Listing listing = optional.get();
            listing.setVehicleNumber(dto.getVehicleNumber());
            listing.setOwnerName(dto.getOwnerName());
            listing.setSellerId(dto.getSellerId());
            listing.setPrice(dto.getPrice());
            listing.setDescription(dto.getDescription());
            listing.setStatus(dto.getStatus());
            listing.setCreatedAt(dto.getCreatedAt());
            listing.setPriceNegotiable(dto.isPriceNegotiable());
            listing.setFeatures(dto.getFeatures());
            listing.setVehicleDocuments(dto.getVehicleDocuments());
            listing.setImages(dto.getImages());
            listing.setLocation(dto.getLocation());
            listing.setContactNumber(dto.getContactNumber());
            listing.setListingType(dto.getListingType());
            listing.setMake(dto.getMake());
            listing.setModel(dto.getModel());
            listing.setYear(dto.getYear());
            listing.setType(dto.getType());
            listing.setColor(dto.getColor());
            listing.setMileage(dto.getMileage());
            listing.setDailyRate(dto.getDailyRate());
            listing.setAvailable(dto.isAvailable());
            listing.setKmDriven(dto.getKmDriven());
            listing.setSelfDriving(dto.isSelfDriving());
            listing.setWithDriver(dto.isWithDriver());
            listing.setAvailabilityStartDate(dto.getAvailabilityStartDate());
            listing.setAvailabilityEndDate(dto.getAvailabilityEndDate());
            // Map ratings
            if (dto.getRatings() != null) {
                java.util.List<Rating> ratings = new java.util.ArrayList<>();
                for (RatingDTO r : dto.getRatings()) {
                    ratings.add(new Rating(r.getScore(), r.getReview(), LocalDateTime.now()));
                }
                listing.setRatings(ratings);
            }
            Listing updated = listingRepository.save(listing);
            return toDTO(updated);
        }
        return null;
    }

    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    public List<ListingDTO> getListingsByListingType(String listingType) {
        return listingRepository.findByListingType(listingType).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ListingDTO toDTO(Listing listing) {
        ListingDTO dto = new ListingDTO();
        dto.setId(listing.getId());
        dto.setVehicleNumber(listing.getVehicleNumber());
        dto.setOwnerName(listing.getOwnerName());
        dto.setSellerId(listing.getSellerId());
        dto.setPrice(listing.getPrice());
        dto.setDescription(listing.getDescription());
        dto.setStatus(listing.getStatus());
        dto.setCreatedAt(listing.getCreatedAt());
        dto.setPriceNegotiable(listing.isPriceNegotiable());
        dto.setFeatures(listing.getFeatures());
        dto.setVehicleDocuments(listing.getVehicleDocuments());
        dto.setImages(listing.getImages());
        dto.setLocation(listing.getLocation());
        dto.setContactNumber(listing.getContactNumber());
        dto.setListingType(listing.getListingType());
        dto.setMake(listing.getMake());
        dto.setModel(listing.getModel());
        dto.setYear(listing.getYear());
        dto.setType(listing.getType());
        dto.setColor(listing.getColor());
        dto.setMileage(listing.getMileage());
        dto.setDailyRate(listing.getDailyRate());
        dto.setAvailable(listing.isAvailable());
        dto.setKmDriven(listing.getKmDriven());
        dto.setSelfDriving(listing.isSelfDriving());
        dto.setWithDriver(listing.isWithDriver());
        dto.setAvailabilityStartDate(listing.getAvailabilityStartDate());
        dto.setAvailabilityEndDate(listing.getAvailabilityEndDate());
        // Map ratings to RatingDTO
        if (listing.getRatings() != null) {
            java.util.List<RatingDTO> ratingDTOs = new java.util.ArrayList<>();
            for (Rating r : listing.getRatings()) {
                ratingDTOs.add(new RatingDTO(r.getScore(), r.getReview()));
            }
            dto.setRatings(ratingDTOs);
        }
        return dto;
    }

    private Listing toEntity(ListingDTO dto) {
        Listing listing = new Listing();
        listing.setVehicleNumber(dto.getVehicleNumber());
        listing.setOwnerName(dto.getOwnerName());
        listing.setSellerId(dto.getSellerId());
        listing.setPrice(dto.getPrice());
        listing.setDescription(dto.getDescription());
        listing.setStatus(dto.getStatus());
        listing.setCreatedAt(dto.getCreatedAt());
        listing.setPriceNegotiable(dto.isPriceNegotiable());
        listing.setFeatures(dto.getFeatures());
        listing.setVehicleDocuments(dto.getVehicleDocuments());
        listing.setImages(dto.getImages());
        listing.setLocation(dto.getLocation());
        listing.setContactNumber(dto.getContactNumber());
        listing.setListingType(dto.getListingType());
        listing.setMake(dto.getMake());
        listing.setModel(dto.getModel());
        listing.setYear(dto.getYear());
        listing.setType(dto.getType());
        listing.setColor(dto.getColor());
        listing.setMileage(dto.getMileage());
        listing.setDailyRate(dto.getDailyRate());
        listing.setAvailable(dto.isAvailable());
        listing.setKmDriven(dto.getKmDriven());
        listing.setSelfDriving(dto.isSelfDriving());
        listing.setWithDriver(dto.isWithDriver());
        listing.setAvailabilityStartDate(dto.getAvailabilityStartDate());
        listing.setAvailabilityEndDate(dto.getAvailabilityEndDate());
        // Map RatingDTO to Rating
        if (dto.getRatings() != null) {
            java.util.List<Rating> ratings = new java.util.ArrayList<>();
            for (RatingDTO r : dto.getRatings()) {
                ratings.add(new Rating(r.getScore(), r.getReview(), LocalDateTime.now()));
            }
            listing.setRatings(ratings);
        }
        return listing;
    }
} 