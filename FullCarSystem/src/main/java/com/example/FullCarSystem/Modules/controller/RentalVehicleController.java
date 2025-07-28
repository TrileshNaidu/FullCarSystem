package com.example.FullCarSystem.Modules.controller;

import com.example.FullCarSystem.Modules.DTO.RentalVehicleDTO;
import com.example.FullCarSystem.Modules.Service.RentalVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import com.example.FullCarSystem.Modules.model.Rating;
import com.example.FullCarSystem.Modules.model.RentalVehicle;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import org.springframework.security.core.Authentication;
import com.example.FullCarSystem.Modules.Service.UserService;

@RestController
@RequestMapping("/api/rental-vehicles")
public class RentalVehicleController {
    @Autowired
    private RentalVehicleService rentalVehicleService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<RentalVehicleDTO> getAllRentalVehicles() {
        return rentalVehicleService.getAllRentalVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalVehicleDTO> getRentalVehicleById(@PathVariable Long id) {
        RentalVehicleDTO dto = rentalVehicleService.getRentalVehicleById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public RentalVehicleDTO createRentalVehicle(@RequestBody RentalVehicleDTO dto, @RequestParam(defaultValue = "false") boolean admin) {
        return rentalVehicleService.createRentalVehicle(dto, admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalVehicleDTO> updateRentalVehicle(@PathVariable Long id, @RequestBody RentalVehicleDTO dto, Authentication authentication) {
        RentalVehicleDTO existing = rentalVehicleService.getRentalVehicleById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or owner
        if (!isAdmin && (existing.getOwnerId() == null || !existing.getOwnerId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        RentalVehicleDTO updated = rentalVehicleService.updateRentalVehicle(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<RentalVehicleDTO> approveRentalVehicle(@PathVariable Long id) {
        RentalVehicleDTO updated = rentalVehicleService.approveRentalVehicle(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<RentalVehicleDTO> rejectRentalVehicle(@PathVariable Long id) {
        RentalVehicleDTO updated = rentalVehicleService.rejectRentalVehicle(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadRentalVehicleImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        String uploadDir = "uploads/images/rentalvehicles/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/uploadDocument")
    public ResponseEntity<String> uploadRentalVehicleDocument(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only PDF documents are allowed");
        }
        String uploadDir = "uploads/docs/rentalvehicles/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadRentalVehicleImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        RentalVehicleDTO rentalVehicle = rentalVehicleService.getRentalVehicleById(id);
        if (rentalVehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental vehicle not found");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images" + File.separator + "rentalvehicles" + File.separator + id + File.separator;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/{id}/uploadDocument")
    public ResponseEntity<String> uploadRentalVehicleDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        RentalVehicleDTO rentalVehicle = rentalVehicleService.getRentalVehicleById(id);
        if (rentalVehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental vehicle not found");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only PDF documents are allowed");
        }
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "docs" + File.separator + "rentalvehicles" + File.separator + id + File.separator;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<Void> addRentalVehicleRating(@PathVariable Long id, @RequestBody RatingDTO rating, Authentication authentication) {
        RentalVehicleDTO rentalVehicle = getRentalVehicleById(id).getBody();
        if (rentalVehicle == null) return ResponseEntity.notFound().build();
        if (rentalVehicle.getRatings() == null) rentalVehicle.setRatings(new ArrayList<>());
        rentalVehicle.getRatings().add(rating);
        updateRentalVehicle(id, rentalVehicle, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<java.util.List<com.example.FullCarSystem.Modules.DTO.RatingDTO>> getRentalVehicleRatings(@PathVariable Long id) {
        RentalVehicleDTO rentalVehicle = getRentalVehicleById(id).getBody();
        if (rentalVehicle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rentalVehicle.getRatings());
    }

    @GetMapping("/approved")
    public List<RentalVehicleDTO> getAllApprovedRentalVehicles() {
        return rentalVehicleService.getRentalVehiclesByStatus(com.example.FullCarSystem.Modules.model.RentalVehicle.Status.APPROVED);
    }

    @GetMapping("/rejected")
    public List<RentalVehicleDTO> getAllRejectedRentalVehicles() {
        return rentalVehicleService.getRentalVehiclesByStatus(com.example.FullCarSystem.Modules.model.RentalVehicle.Status.REJECTED);
    }

    @GetMapping("/pending")
    public List<RentalVehicleDTO> getAllPendingRentalVehicles() {
        return rentalVehicleService.getRentalVehiclesByStatus(com.example.FullCarSystem.Modules.model.RentalVehicle.Status.PENDING);
    }

    @GetMapping("/user")
    public List<RentalVehicleDTO> getRentalVehiclesForCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getId();
        return rentalVehicleService.getRentalVehiclesByOwnerId(userId);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getRentalVehicleStatus(@PathVariable Long id) {
        RentalVehicleDTO dto = rentalVehicleService.getRentalVehicleById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto.getStatus().name());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalVehicle(@PathVariable Long id, Authentication authentication) {
        RentalVehicleDTO existing = rentalVehicleService.getRentalVehicleById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or owner
        if (!isAdmin && (existing.getOwnerId() == null || !existing.getOwnerId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        rentalVehicleService.deleteRentalVehicle(id);
        return ResponseEntity.noContent().build();
    }
} 