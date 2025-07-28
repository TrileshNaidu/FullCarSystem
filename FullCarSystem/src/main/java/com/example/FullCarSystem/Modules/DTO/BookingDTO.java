package com.example.FullCarSystem.Modules.DTO;

import java.time.LocalDate;

public class BookingDTO {
    private Long id;
    private Long rentalVehicleId;
    private Long renterId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;
    private String review; // nullable, for user review
    private Integer rating; // nullable, for user rating
    private String deliveryAddress;
    private boolean withDriver;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRentalVehicleId() { return rentalVehicleId; }
    public void setRentalVehicleId(Long rentalVehicleId) { this.rentalVehicleId = rentalVehicleId; }
    public Long getRenterId() { return renterId; }
    public void setRenterId(Long renterId) { this.renterId = renterId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public boolean isWithDriver() { return withDriver; }
    public void setWithDriver(boolean withDriver) { this.withDriver = withDriver; }

    public enum BookingStatus {
        PENDING, BOOKED, CANCELLED, COMPLETED
    }
} 