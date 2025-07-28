package com.example.FullCarSystem.Modules.Service;

import com.example.FullCarSystem.Modules.model.Booking;
import com.example.FullCarSystem.Modules.model.Booking.BookingStatus;
import com.example.FullCarSystem.Modules.model.RentalVehicle;
import com.example.FullCarSystem.Modules.model.DeliveryTask;
import com.example.FullCarSystem.Modules.model.DeliveryTask.DeliveryStatus;
import com.example.FullCarSystem.Modules.DTO.BookingDTO;
import com.example.FullCarSystem.Modules.DTO.BookingRequest;
import com.example.FullCarSystem.Modules.repository.BookingRepository;
import com.example.FullCarSystem.Modules.repository.RentalVehicleRepository;
import com.example.FullCarSystem.Modules.repository.DeliveryTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RentalVehicleRepository rentalVehicleRepository;
    @Autowired
    private DeliveryTaskRepository deliveryTaskRepository;

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.map(this::toDTO).orElse(null);
    }

    public BookingDTO createBooking(BookingRequest request) {
        // Double-booking prevention
        List<Booking> overlapping = bookingRepository.findByRentalVehicle_IdAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
                request.getRentalVehicleId(), request.getStartDate(), request.getEndDate());
        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Vehicle is already booked for the selected dates.");
        }
        Booking booking = new Booking();
        Optional<RentalVehicle> vehicle = rentalVehicleRepository.findById(request.getRentalVehicleId());
        vehicle.ifPresent(booking::setRentalVehicle);
        booking.setRenterId(request.getRenterId());
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setDeliveryAddress(request.getDeliveryAddress());
        booking.setWithDriver(request.isWithDriver());
        booking.setStatus(BookingStatus.PENDING);
        Booking saved = bookingRepository.save(booking);
        // Auto-create DeliveryTask
        DeliveryTask deliveryTask = new DeliveryTask();
        deliveryTask.setBooking(saved);
        deliveryTask.setDeliveryStatus(DeliveryStatus.PENDING);
        deliveryTask.setScheduledTime(saved.getStartDate() != null ? saved.getStartDate().atStartOfDay() : null);
        deliveryTask.setDriverName("");
        deliveryTask.setDriverContact("");
        deliveryTask.setCreatedAt(java.time.LocalDateTime.now());
        deliveryTaskRepository.save(deliveryTask);
        return toDTO(saved);
    }

    public BookingDTO updateBooking(Long id, BookingDTO dto) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            if (dto.getRentalVehicleId() != null) {
                Optional<RentalVehicle> vehicle = rentalVehicleRepository.findById(dto.getRentalVehicleId());
                vehicle.ifPresent(booking::setRentalVehicle);
            }
            booking.setRenterId(dto.getRenterId());
            booking.setStartDate(dto.getStartDate());
            booking.setEndDate(dto.getEndDate());
            if (dto.getStatus() != null) {
                booking.setStatus(BookingStatus.valueOf(dto.getStatus().name()));
            }
            booking.setDeliveryAddress(dto.getDeliveryAddress());
            booking.setWithDriver(dto.isWithDriver());
            booking.setRating(dto.getRating());
            booking.setReview(dto.getReview());
            Booking updated = bookingRepository.save(booking);
            return toDTO(updated);
        }
        return null;
    }

    public BookingDTO updateBookingStatus(Long id, String statusStr) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            BookingStatus current = booking.getStatus();
            BookingStatus newStatus;
            try {
                newStatus = BookingStatus.valueOf(statusStr);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid status value");
            }
            // Enforce valid transitions
            if (current == BookingStatus.CANCELLED || current == BookingStatus.COMPLETED) {
                throw new IllegalStateException("Cannot change status from " + current);
            }
            if (current == BookingStatus.PENDING && (newStatus == BookingStatus.BOOKED || newStatus == BookingStatus.CANCELLED)) {
                booking.setStatus(newStatus);
            } else if (current == BookingStatus.BOOKED && (newStatus == BookingStatus.COMPLETED || newStatus == BookingStatus.CANCELLED)) {
                booking.setStatus(newStatus);
            } else if (current == newStatus) {
                // No change
            } else {
                throw new IllegalStateException("Invalid status transition from " + current + " to " + newStatus);
            }
            Booking updated = bookingRepository.save(booking);
            return toDTO(updated);
        }
        return null;
    }

    public BookingDTO cancelBooking(Long id) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.COMPLETED) {
                throw new IllegalStateException("Cannot cancel a completed or already cancelled booking");
            }
            booking.setStatus(BookingStatus.CANCELLED);
            Booking updated = bookingRepository.save(booking);
            return toDTO(updated);
        }
        return null;
    }

    public List<BookingDTO> getBookingsByUser(Long userId) {
        return bookingRepository.findByRenterId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO addReview(Long id, Integer rating, String review) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            booking.setRating(rating);
            booking.setReview(review);
            Booking updated = bookingRepository.save(booking);
            return toDTO(updated);
        }
        return null;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setRentalVehicleId(booking.getRentalVehicle() != null ? booking.getRentalVehicle().getId() : null);
        dto.setRenterId(booking.getRenterId());
        dto.setStartDate(booking.getStartDate());
        dto.setEndDate(booking.getEndDate());
        dto.setStatus(booking.getStatus() != null ? BookingDTO.BookingStatus.valueOf(booking.getStatus().name()) : null);
        dto.setDeliveryAddress(booking.getDeliveryAddress());
        dto.setWithDriver(booking.isWithDriver());
        dto.setRating(booking.getRating());
        dto.setReview(booking.getReview());
        return dto;
    }
} 