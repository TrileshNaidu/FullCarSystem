package com.example.FullCarSystem.Modules.DTO;

import java.time.LocalDate;

public class BookingRequest {
    private Long rentalVehicleId;
    private Long renterId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String deliveryAddress;
    private boolean withDriver;

    // Getters and setters
    public Long getRentalVehicleId() { return rentalVehicleId; }
    public void setRentalVehicleId(Long rentalVehicleId) { this.rentalVehicleId = rentalVehicleId; }
    public Long getRenterId() { return renterId; }
    public void setRenterId(Long renterId) { this.renterId = renterId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public boolean isWithDriver() { return withDriver; }
    public void setWithDriver(boolean withDriver) { this.withDriver = withDriver; }
} 