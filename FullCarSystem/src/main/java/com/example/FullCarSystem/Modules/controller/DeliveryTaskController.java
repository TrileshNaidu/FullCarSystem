package com.example.FullCarSystem.Modules.controller;

import com.example.FullCarSystem.Modules.DTO.DeliveryTaskDTO;
import com.example.FullCarSystem.Modules.Service.DeliveryTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/delivery-tasks")
public class DeliveryTaskController {
    @Autowired
    private DeliveryTaskService deliveryTaskService;

    @GetMapping
    public List<DeliveryTaskDTO> getAllDeliveryTasks() {
        return deliveryTaskService.getAllDeliveryTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryTaskDTO> getDeliveryTaskById(@PathVariable Long id) {
        DeliveryTaskDTO dto = deliveryTaskService.getDeliveryTaskById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createDeliveryTask(@RequestBody DeliveryTaskDTO dto) {
        if (dto.getBookingId() == null) {
            return ResponseEntity.badRequest().body("bookingId is required");
        }
        try {
            DeliveryTaskDTO created = deliveryTaskService.createDeliveryTask(dto);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryTaskDTO> updateDeliveryTask(@PathVariable Long id, @RequestBody DeliveryTaskDTO dto, Authentication authentication) {
        DeliveryTaskDTO existing = deliveryTaskService.getDeliveryTaskById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or booking's renter
        Long bookingId = existing.getBookingId();
        Long renterId = deliveryTaskService.getRenterIdByBookingId(bookingId);
        if (!isAdmin && (renterId == null || !renterId.toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        DeliveryTaskDTO updated = deliveryTaskService.updateDeliveryTask(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryTask(@PathVariable Long id, Authentication authentication) {
        DeliveryTaskDTO existing = deliveryTaskService.getDeliveryTaskById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        Long bookingId = existing.getBookingId();
        Long renterId = deliveryTaskService.getRenterIdByBookingId(bookingId);
        // Only allow if admin or booking's renter
        if (!isAdmin && (renterId == null || !renterId.toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        deliveryTaskService.deleteDeliveryTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public List<DeliveryTaskDTO> getTasksByDeliveryStatus(@PathVariable String status) {
        try {
            return deliveryTaskService.getTasksByDeliveryStatus(
                com.example.FullCarSystem.Modules.model.DeliveryTask.DeliveryStatus.valueOf(status));
        } catch (Exception e) {
            return List.of();
        }
    }

    @GetMapping("/booking/{bookingId}")
    public List<DeliveryTaskDTO> getTasksByBookingId(@PathVariable Long bookingId) {
        return deliveryTaskService.getTasksByBookingId(bookingId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DeliveryTaskDTO> updateDeliveryStatus(@PathVariable Long id, @RequestBody DeliveryTaskDTO dto) {
        DeliveryTaskDTO existing = deliveryTaskService.getDeliveryTaskById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setDeliveryStatus(dto.getDeliveryStatus());
        DeliveryTaskDTO updated = deliveryTaskService.updateDeliveryTask(id, existing);
        return ResponseEntity.ok(updated);
    }
} 