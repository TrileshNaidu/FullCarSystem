package com.example.FullCarSystem.Modules.controller;

import com.example.FullCarSystem.Modules.DTO.VehicleDTO;
import com.example.FullCarSystem.Modules.Service.VehicleService;
import com.example.FullCarSystem.Modules.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        VehicleDTO dto = vehicleService.getVehicleById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/type/{type}")
    public List<VehicleDTO> getVehiclesByType(@PathVariable String type) {
        return vehicleService.getVehiclesByType(com.example.FullCarSystem.Modules.model.CarType.valueOf(type.toUpperCase()));
    }

    @GetMapping("/make/{make}")
    public List<VehicleDTO> getVehiclesByMake(@PathVariable String make) {
        return vehicleService.getVehiclesByMake(make);
    }

    @GetMapping("/model/{model}")
    public List<VehicleDTO> getVehiclesByModel(@PathVariable String model) {
        return vehicleService.getVehiclesByModel(model);
    }

    @PostMapping
    public VehicleDTO createVehicle(@Valid @RequestBody VehicleDTO dto) {
        return vehicleService.createVehicle(dto);
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadVehicleImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images" + File.separator + "vehicles" + File.separator + id + File.separator;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/{id}/uploadDocument")
    public ResponseEntity<String> uploadVehicleDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only PDF documents are allowed");
        }
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "docs" + File.separator + "vehicles" + File.separator + id + File.separator;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<Void> addVehicleRating(@PathVariable Long id, @RequestBody com.example.FullCarSystem.Modules.DTO.RatingDTO rating) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        if (vehicle == null) return ResponseEntity.notFound().build();
        if (vehicle.getRatings() == null) vehicle.setRatings(new ArrayList<>());
        vehicle.getRatings().add(rating);
        vehicleService.updateVehicle(id, vehicle);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<java.util.List<com.example.FullCarSystem.Modules.DTO.RatingDTO>> getVehicleRatings(@PathVariable Long id) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        if (vehicle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicle.getRatings());
    }

    @GetMapping("/user")
    public List<VehicleDTO> getVehiclesForCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getId();
        return vehicleService.getVehiclesByOwnerId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO dto, Authentication authentication) {
        VehicleDTO existing = vehicleService.getVehicleById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or owner
        if (!isAdmin && (existing.getOwnerId() == null || !existing.getOwnerId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        VehicleDTO updated = vehicleService.updateVehicle(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id, Authentication authentication) {
        VehicleDTO existing = vehicleService.getVehicleById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or owner
        if (!isAdmin && (existing.getOwnerId() == null || !existing.getOwnerId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
} 