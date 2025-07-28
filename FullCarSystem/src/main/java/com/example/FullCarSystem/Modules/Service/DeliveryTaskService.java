package com.example.FullCarSystem.Modules.Service;

import com.example.FullCarSystem.Modules.model.DeliveryTask;
import com.example.FullCarSystem.Modules.model.Booking;
import com.example.FullCarSystem.Modules.DTO.DeliveryTaskDTO;
import com.example.FullCarSystem.Modules.repository.DeliveryTaskRepository;
import com.example.FullCarSystem.Modules.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.FullCarSystem.Modules.model.DeliveryTask.DeliveryStatus;
import java.time.LocalDateTime;

@Service
public class DeliveryTaskService {
    @Autowired
    private DeliveryTaskRepository deliveryTaskRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public List<DeliveryTaskDTO> getAllDeliveryTasks() {
        return deliveryTaskRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DeliveryTaskDTO getDeliveryTaskById(Long id) {
        Optional<DeliveryTask> task = deliveryTaskRepository.findById(id);
        return task.map(this::toDTO).orElse(null);
    }

    public DeliveryTaskDTO createDeliveryTask(DeliveryTaskDTO dto) {
        if (dto.getBookingId() == null) {
            throw new IllegalArgumentException("bookingId is required");
        }
        Optional<Booking> bookingOpt = bookingRepository.findById(dto.getBookingId());
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found for id: " + dto.getBookingId());
        }
        DeliveryTask task = toEntity(dto);
        task.setBooking(bookingOpt.get());
        task.setCreatedAt(LocalDateTime.now());
        DeliveryTask saved = deliveryTaskRepository.save(task);
        return toDTO(saved);
    }

    public DeliveryTaskDTO updateDeliveryTask(Long id, DeliveryTaskDTO dto) {
        Optional<DeliveryTask> optional = deliveryTaskRepository.findById(id);
        if (optional.isPresent()) {
            DeliveryTask task = optional.get();
            if (dto.getBookingId() != null) {
                Optional<Booking> booking = bookingRepository.findById(dto.getBookingId());
                booking.ifPresent(task::setBooking);
            }
            // Status transition logic and timestamps
            DeliveryStatus current = task.getDeliveryStatus();
            DeliveryStatus newStatus = null;
            if (dto.getDeliveryStatus() != null) {
                newStatus = DeliveryStatus.valueOf(dto.getDeliveryStatus().name());
            }
            if (newStatus != null && current != newStatus) {
                if (current == DeliveryStatus.PENDING && newStatus == DeliveryStatus.IN_PROGRESS) {
                    task.setStartedAt(LocalDateTime.now());
                } else if ((current == DeliveryStatus.IN_PROGRESS) && (newStatus == DeliveryStatus.DELIVERED || newStatus == DeliveryStatus.FAILED)) {
                    task.setCompletedAt(LocalDateTime.now());
                } else if (current == newStatus) {
                    // No change
                } else if (current == DeliveryStatus.PENDING && (newStatus == DeliveryStatus.DELIVERED || newStatus == DeliveryStatus.FAILED)) {
                    // Allow skipping IN_PROGRESS
                    task.setStartedAt(LocalDateTime.now());
                    task.setCompletedAt(LocalDateTime.now());
                } else {
                    throw new IllegalStateException("Invalid status transition from " + current + " to " + newStatus);
                }
                task.setDeliveryStatus(newStatus);
            }
            // Driver assignment/unassignment
            task.setDriverName(dto.getDriverName());
            task.setDriverContact(dto.getDriverContact());
            task.setScheduledTime(dto.getScheduledTime());
            DeliveryTask updated = deliveryTaskRepository.save(task);
            return toDTO(updated);
        }
        return null;
    }

    public void deleteDeliveryTask(Long id) {
        deliveryTaskRepository.deleteById(id);
    }

    public List<DeliveryTaskDTO> getTasksByDeliveryStatus(DeliveryStatus status) {
        return deliveryTaskRepository.findByDeliveryStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DeliveryTaskDTO> getTasksByBookingId(Long bookingId) {
        return deliveryTaskRepository.findByBooking_Id(bookingId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Long getRenterIdByBookingId(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return booking.map(Booking::getRenterId).orElse(null);
    }

    private DeliveryTaskDTO toDTO(DeliveryTask task) {
        DeliveryTaskDTO dto = new DeliveryTaskDTO();
        dto.setId(task.getId());
        dto.setBookingId(task.getBooking() != null ? task.getBooking().getId() : null);
        dto.setDeliveryStatus(task.getDeliveryStatus() != null ? DeliveryTaskDTO.DeliveryStatus.valueOf(task.getDeliveryStatus().name()) : null);
        dto.setScheduledTime(task.getScheduledTime());
        dto.setDriverName(task.getDriverName());
        dto.setDriverContact(task.getDriverContact());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setStartedAt(task.getStartedAt());
        dto.setCompletedAt(task.getCompletedAt());
        return dto;
    }

    private DeliveryTask toEntity(DeliveryTaskDTO dto) {
        DeliveryTask task = new DeliveryTask();
        if (dto.getBookingId() != null) {
            Optional<Booking> booking = bookingRepository.findById(dto.getBookingId());
            booking.ifPresent(task::setBooking);
        }
        task.setDeliveryStatus(dto.getDeliveryStatus() != null ? DeliveryStatus.valueOf(dto.getDeliveryStatus().name()) : null);
        task.setScheduledTime(dto.getScheduledTime());
        task.setDriverName(dto.getDriverName());
        task.setDriverContact(dto.getDriverContact());
        task.setCreatedAt(dto.getCreatedAt());
        task.setStartedAt(dto.getStartedAt());
        task.setCompletedAt(dto.getCompletedAt());
        return task;
    }
} 