package com.example.FullCarSystem.Modules.controller;

import com.example.FullCarSystem.Modules.DTO.BookingDTO;
import com.example.FullCarSystem.Modules.DTO.BookingRequest;
import com.example.FullCarSystem.Modules.Service.BookingService;
import com.example.FullCarSystem.Modules.model.Booking.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO dto = bookingService.getBookingById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        try {
            return ResponseEntity.ok(bookingService.createBooking(request));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO dto, Authentication authentication) {
        BookingDTO existing = bookingService.getBookingById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or renter
        if (!isAdmin && (existing.getRenterId() == null || !existing.getRenterId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        BookingDTO updated = bookingService.updateBooking(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        try {
            BookingDTO updated = bookingService.updateBookingStatus(id, statusRequest.getStatus());
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            BookingDTO updated = bookingService.cancelBooking(id);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<BookingDTO> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<BookingDTO> addReview(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        BookingDTO updated = bookingService.addReview(id, reviewRequest.getRating(), reviewRequest.getReview());
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id, Authentication authentication) {
        BookingDTO existing = bookingService.getBookingById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or renter
        if (!isAdmin && (existing.getRenterId() == null || !existing.getRenterId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

class StatusRequest {
    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BookingStatus getStatusEnum() {
        try {
            return BookingStatus.valueOf(status);
        } catch (Exception e) {
            return null;
        }
    }
}
class ReviewRequest {
    private Integer rating;
    private String review;
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
} 