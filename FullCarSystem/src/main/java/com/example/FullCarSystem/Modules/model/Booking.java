package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "rental_vehicle_id")
    private RentalVehicle rentalVehicle;
    private Long renterId; // Reference to User (by id)
    private LocalDate startDate;
    private LocalDate endDate;
    public enum BookingStatus {
        PENDING, BOOKED, CANCELLED, COMPLETED
    }
    @Enumerated(EnumType.STRING)
    private BookingStatus status; // e.g., PENDING, BOOKED, CANCELLED, COMPLETED
    private String review; // nullable, for user review
    private Integer rating; // nullable, for user rating
    private String deliveryAddress;
    private boolean withDriver;

    // Constructors, getters, setters
    public Booking() {}

    public Booking(RentalVehicle rentalVehicle, Long renterId, LocalDate startDate, LocalDate endDate, BookingStatus status, String deliveryAddress, boolean withDriver) {
        this.rentalVehicle = rentalVehicle;
        this.renterId = renterId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.withDriver = withDriver;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public RentalVehicle getRentalVehicle() { return rentalVehicle; }
    public void setRentalVehicle(RentalVehicle rentalVehicle) { this.rentalVehicle = rentalVehicle; }
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
} 