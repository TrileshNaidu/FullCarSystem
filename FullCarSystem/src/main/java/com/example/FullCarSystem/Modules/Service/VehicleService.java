package com.example.FullCarSystem.Modules.Service;

import com.example.FullCarSystem.Modules.model.Vehicle;
import com.example.FullCarSystem.Modules.DTO.VehicleDTO;
import com.example.FullCarSystem.Modules.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.FullCarSystem.Modules.model.CarType;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import com.example.FullCarSystem.Modules.model.Rating;
import java.time.LocalDateTime;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAllByOrderByIdAsc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO getVehicleById(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return vehicle.map(this::toDTO).orElse(null);
    }

    public VehicleDTO createVehicle(VehicleDTO dto) {
        Vehicle vehicle = toEntity(dto);
        vehicle.setOwnerId(dto.getOwnerId());
        Vehicle saved = vehicleRepository.save(vehicle);
        return toDTO(saved);
    }

    public VehicleDTO updateVehicle(Long id, VehicleDTO dto) {
        Optional<Vehicle> optional = vehicleRepository.findById(id);
        if (optional.isPresent()) {
            Vehicle vehicle = optional.get();
            vehicle.setMake(dto.getMake());
            vehicle.setModel(dto.getModel());
            vehicle.setYear(dto.getYear());
            vehicle.setType(dto.getType());
            vehicle.setColor(dto.getColor());
            vehicle.setMileage(dto.getMileage());
            vehicle.setVehicleNumber(dto.getVehicleNumber());
            vehicle.setKmDriven(dto.getKmDriven());
            vehicle.setOwnerId(dto.getOwnerId());
            Vehicle updated = vehicleRepository.save(vehicle);
            return toDTO(updated);
        }
        return null;
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<VehicleDTO> getVehiclesByType(CarType type) {
        return vehicleRepository.findByType(type).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> getVehiclesByMake(String make) {
        return vehicleRepository.findByMakeIgnoreCase(make).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> getVehiclesByModel(String model) {
        return vehicleRepository.findByModelIgnoreCase(model).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> getVehiclesByOwnerId(Long ownerId) {
        return vehicleRepository.findByOwnerId(ownerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private VehicleDTO toDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setType(vehicle.getType());
        dto.setColor(vehicle.getColor());
        dto.setMileage(vehicle.getMileage());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setKmDriven(vehicle.getKmDriven());
        dto.setPriceNegotiable(vehicle.isPriceNegotiable());
        dto.setFeatures(vehicle.getFeatures());
        dto.setVehicleDocuments(vehicle.getVehicleDocuments());
        dto.setImages(vehicle.getImages());
        dto.setLocation(vehicle.getLocation());
        dto.setContactNumber(vehicle.getContactNumber());
        dto.setOwnerId(vehicle.getOwnerId());
        // Map ratings to RatingDTO
        if (vehicle.getRatings() != null) {
            java.util.List<RatingDTO> ratingDTOs = new java.util.ArrayList<>();
            for (Rating r : vehicle.getRatings()) {
                ratingDTOs.add(new RatingDTO(r.getScore(), r.getReview()));
            }
            dto.setRatings(ratingDTOs);
        }
        return dto;
    }

    private Vehicle toEntity(VehicleDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setType(dto.getType());
        vehicle.setColor(dto.getColor());
        vehicle.setMileage(dto.getMileage());
        vehicle.setVehicleNumber(dto.getVehicleNumber());
        vehicle.setKmDriven(dto.getKmDriven());
        vehicle.setPriceNegotiable(dto.isPriceNegotiable());
        vehicle.setFeatures(dto.getFeatures());
        vehicle.setVehicleDocuments(dto.getVehicleDocuments());
        vehicle.setImages(dto.getImages());
        vehicle.setLocation(dto.getLocation());
        vehicle.setContactNumber(dto.getContactNumber());
        vehicle.setOwnerId(dto.getOwnerId());
        // Map RatingDTO to Rating
        if (dto.getRatings() != null) {
            java.util.List<Rating> ratings = new java.util.ArrayList<>();
            for (RatingDTO r : dto.getRatings()) {
                ratings.add(new Rating(r.getScore(), r.getReview(), LocalDateTime.now()));
            }
            vehicle.setRatings(ratings);
        }
        return vehicle;
    }
} 