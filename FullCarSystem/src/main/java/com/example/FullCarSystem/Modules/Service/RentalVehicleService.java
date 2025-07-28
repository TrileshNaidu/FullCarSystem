package com.example.FullCarSystem.Modules.Service;

import com.example.FullCarSystem.Modules.model.RentalVehicle;
import com.example.FullCarSystem.Modules.DTO.RentalVehicleDTO;
import com.example.FullCarSystem.Modules.repository.RentalVehicleRepository;
import com.example.FullCarSystem.Modules.model.CarType;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import com.example.FullCarSystem.Modules.model.Rating;
import com.example.FullCarSystem.Modules.DTO.DateRangeDTO;
import com.example.FullCarSystem.Modules.model.DateRange;
import com.example.FullCarSystem.Modules.Service.ListingService;
import com.example.FullCarSystem.Modules.DTO.ListingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class RentalVehicleService {
    @Autowired
    private RentalVehicleRepository rentalVehicleRepository;
    @Autowired
    private ListingService listingService;

    public List<RentalVehicleDTO> getAllRentalVehicles() {
        return rentalVehicleRepository.findByStatus(RentalVehicle.Status.APPROVED).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RentalVehicleDTO getRentalVehicleById(Long id) {
        Optional<RentalVehicle> vehicle = rentalVehicleRepository.findById(id);
        return vehicle.map(this::toDTO).orElse(null);
    }

    public RentalVehicleDTO createRentalVehicle(RentalVehicleDTO dto, boolean isAdmin) {
        RentalVehicle vehicle = toEntity(dto);
        vehicle.setStatus(isAdmin ? RentalVehicle.Status.APPROVED : RentalVehicle.Status.PENDING);
        vehicle.setVehicleNumber(dto.getVehicleNumber());
        vehicle.setOwnerId(dto.getOwnerId());
        RentalVehicle saved = rentalVehicleRepository.save(vehicle);
        return toDTO(saved);
    }

    public RentalVehicleDTO updateRentalVehicle(Long id, RentalVehicleDTO dto) {
        Optional<RentalVehicle> optional = rentalVehicleRepository.findById(id);
        if (optional.isPresent()) {
            RentalVehicle vehicle = optional.get();
            vehicle.setMake(dto.getMake());
            vehicle.setModel(dto.getModel());
            vehicle.setYear(dto.getYear());
            vehicle.setType(dto.getType());
            vehicle.setColor(dto.getColor());
            vehicle.setMileage(dto.getMileage());
            vehicle.setVehicleNumber(dto.getVehicleNumber());
            vehicle.setDailyRate(dto.getDailyRate());
            vehicle.setAvailable(dto.isAvailable());
            vehicle.setKmDriven(dto.getKmDriven());
            vehicle.setSelfDriving(dto.isSelfDriving());
            vehicle.setWithDriver(dto.isWithDriver());
            vehicle.setStatus(dto.getStatus());
            vehicle.setPriceNegotiable(dto.isPriceNegotiable());
            vehicle.setFeatures(dto.getFeatures());
            vehicle.setVehicleDocuments(dto.getVehicleDocuments());
            vehicle.setImages(dto.getImages());
            vehicle.setLocation(dto.getLocation());
            vehicle.setContactNumber(dto.getContactNumber());
            vehicle.setAvailabilityStartDate(dto.getAvailabilityStartDate());
            vehicle.setAvailabilityEndDate(dto.getAvailabilityEndDate());
            vehicle.setOwnerId(dto.getOwnerId());
            // Merge ratings
            if (dto.getRatings() != null && !dto.getRatings().isEmpty()) {
                List<Rating> existingRatings = vehicle.getRatings() != null ? vehicle.getRatings() : new java.util.ArrayList<>();
                for (RatingDTO r : dto.getRatings()) {
                    existingRatings.add(new Rating(r.getScore(), r.getReview(), LocalDateTime.now()));
                }
                vehicle.setRatings(existingRatings);
            }
            RentalVehicle updated = rentalVehicleRepository.save(vehicle);
            return toDTO(updated);
        }
        return null;
    }

    public RentalVehicleDTO approveRentalVehicle(Long id) {
        Optional<RentalVehicle> optional = rentalVehicleRepository.findById(id);
        if (optional.isPresent()) {
            RentalVehicle vehicle = optional.get();
            vehicle.setStatus(RentalVehicle.Status.APPROVED);
            RentalVehicle updated = rentalVehicleRepository.save(vehicle);
            // Create a listing if not already present for this vehicleNumber
            boolean listingExists = listingService.getAllListings().stream()
                .anyMatch(listing -> listing.getVehicleNumber() != null && listing.getVehicleNumber().equals(updated.getVehicleNumber()));
            if (!listingExists) {
                ListingDTO listingDTO = new ListingDTO();
                listingDTO.setSellerId(null); // Set sellerId if available from vehicle/user
                listingDTO.setOwnerName(updated.getOwnerName()); // Set ownerName from rental vehicle
                listingDTO.setVehicleNumber(updated.getVehicleNumber());
                listingDTO.setPrice(updated.getDailyRate());
                listingDTO.setDescription("Rental vehicle listing");
                listingDTO.setStatus("ACTIVE");
                listingDTO.setCreatedAt(LocalDateTime.now());
                listingDTO.setPriceNegotiable(updated.isPriceNegotiable());
                listingDTO.setFeatures(updated.getFeatures());
                listingDTO.setVehicleDocuments(updated.getVehicleDocuments());
                listingDTO.setImages(updated.getImages());
                listingDTO.setLocation(updated.getLocation());
                listingDTO.setContactNumber(updated.getContactNumber());
                // Car details
                listingDTO.setMake(updated.getMake());
                listingDTO.setModel(updated.getModel());
                listingDTO.setYear(updated.getYear());
                listingDTO.setType(updated.getType() != null ? updated.getType().toString() : null);
                listingDTO.setColor(updated.getColor());
                listingDTO.setMileage(updated.getMileage());
                listingDTO.setDailyRate(updated.getDailyRate());
                listingDTO.setAvailable(updated.isAvailable());
                listingDTO.setKmDriven(updated.getKmDriven());
                listingDTO.setSelfDriving(updated.isSelfDriving());
                listingDTO.setWithDriver(updated.isWithDriver());
                listingDTO.setAvailabilityStartDate(updated.getAvailabilityStartDate());
                listingDTO.setAvailabilityEndDate(updated.getAvailabilityEndDate());
                // Convert ratings to RatingDTO
                if (updated.getRatings() != null) {
                    java.util.List<RatingDTO> ratingDTOs = new java.util.ArrayList<>();
                    for (Rating r : updated.getRatings()) {
                        ratingDTOs.add(new RatingDTO(r.getScore(), r.getReview()));
                    }
                    listingDTO.setRatings(ratingDTOs);
                }
                listingDTO.setListingType("RENTAL");
                listingService.createListing(listingDTO);
            }
            return toDTO(updated);
        }
        return null;
    }

    public RentalVehicleDTO rejectRentalVehicle(Long id) {
        Optional<RentalVehicle> optional = rentalVehicleRepository.findById(id);
        if (optional.isPresent()) {
            RentalVehicle vehicle = optional.get();
            vehicle.setStatus(RentalVehicle.Status.REJECTED);
            RentalVehicle updated = rentalVehicleRepository.save(vehicle);
            return toDTO(updated);
        }
        return null;
    }

    public void deleteRentalVehicle(Long id) {
        rentalVehicleRepository.deleteById(id);
    }

    public List<RentalVehicleDTO> getRentalVehiclesByStatus(RentalVehicle.Status status) {
        return rentalVehicleRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<RentalVehicleDTO> getRentalVehiclesByOwnerId(Long ownerId) {
        return rentalVehicleRepository.findByOwnerId(ownerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private RentalVehicleDTO toDTO(RentalVehicle vehicle) {
        RentalVehicleDTO dto = new RentalVehicleDTO();
        dto.setId(vehicle.getId());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setType(vehicle.getType());
        dto.setColor(vehicle.getColor());
        dto.setMileage(vehicle.getMileage());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setDailyRate(vehicle.getDailyRate());
        dto.setAvailable(vehicle.isAvailable());
        dto.setKmDriven(vehicle.getKmDriven());
        dto.setSelfDriving(vehicle.isSelfDriving());
        dto.setWithDriver(vehicle.isWithDriver());
        dto.setStatus(vehicle.getStatus());
        dto.setPriceNegotiable(vehicle.isPriceNegotiable());
        dto.setFeatures(vehicle.getFeatures());
        dto.setVehicleDocuments(vehicle.getVehicleDocuments());
        dto.setImages(vehicle.getImages());
        dto.setLocation(vehicle.getLocation());
        dto.setContactNumber(vehicle.getContactNumber());
        dto.setOwnerName(vehicle.getOwnerName());
        dto.setOwnerId(vehicle.getOwnerId());
        // Map ratings to RatingDTO
        if (vehicle.getRatings() != null) {
            java.util.List<RatingDTO> ratingDTOs = new java.util.ArrayList<>();
            for (Rating r : vehicle.getRatings()) {
                ratingDTOs.add(new RatingDTO(r.getScore(), r.getReview()));
            }
            dto.setRatings(ratingDTOs);
        }
        dto.setAvailabilityStartDate(vehicle.getAvailabilityStartDate());
        dto.setAvailabilityEndDate(vehicle.getAvailabilityEndDate());
        return dto;
    }

    private RentalVehicle toEntity(RentalVehicleDTO dto) {
        RentalVehicle vehicle = new RentalVehicle();
        vehicle.setId(dto.getId());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setType(dto.getType());
        vehicle.setColor(dto.getColor());
        vehicle.setMileage(dto.getMileage());
        vehicle.setVehicleNumber(dto.getVehicleNumber());
        vehicle.setDailyRate(dto.getDailyRate());
        vehicle.setAvailable(dto.isAvailable());
        vehicle.setKmDriven(dto.getKmDriven());
        vehicle.setSelfDriving(dto.isSelfDriving());
        vehicle.setWithDriver(dto.isWithDriver());
        vehicle.setStatus(dto.getStatus());
        vehicle.setPriceNegotiable(dto.isPriceNegotiable());
        vehicle.setFeatures(dto.getFeatures());
        vehicle.setVehicleDocuments(dto.getVehicleDocuments());
        vehicle.setImages(dto.getImages());
        vehicle.setLocation(dto.getLocation());
        vehicle.setContactNumber(dto.getContactNumber());
        vehicle.setOwnerName(dto.getOwnerName());
        vehicle.setOwnerId(dto.getOwnerId());
        // Map RatingDTO to Rating
        if (dto.getRatings() != null) {
            java.util.List<Rating> ratings = new java.util.ArrayList<>();
            for (RatingDTO r : dto.getRatings()) {
                ratings.add(new Rating(r.getScore(), r.getReview(), LocalDateTime.now()));
            }
            vehicle.setRatings(ratings);
        }
        vehicle.setAvailabilityStartDate(dto.getAvailabilityStartDate());
        vehicle.setAvailabilityEndDate(dto.getAvailabilityEndDate());
        return vehicle;
    }
} 