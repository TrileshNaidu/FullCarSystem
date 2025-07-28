package com.example.FullCarSystem.Modules.DTO;

import com.example.FullCarSystem.Modules.model.CarType;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import com.example.FullCarSystem.Modules.DTO.DateRangeDTO;

public class RentalVehicleDTO {
    private Long id;
    private String make;
    private String model;
    private int year;
    private CarType type;
    private String color;
    private int mileage;
    private double dailyRate;
    private boolean available;
    private int kmDriven;
    private boolean selfDriving;
    private boolean withDriver;
    private com.example.FullCarSystem.Modules.model.RentalVehicle.Status status;
    private boolean priceNegotiable;
    private java.util.List<String> features;
    private java.util.List<String> vehicleDocuments; // URLs or file paths to PDFs
    private java.util.List<RatingDTO> ratings; // Use RatingDTO for ratings
    private java.util.List<String> images; // URLs or file paths to images
    private String location;
    private String contactNumber;
    private String availabilityStartDate;
    private String availabilityEndDate;
    private String ownerName;
    private String vehicleNumber;
    private Long ownerId;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public CarType getType() { return type; }
    public void setType(CarType type) { this.type = type; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public double getDailyRate() { return dailyRate; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public int getKmDriven() { return kmDriven; }
    public void setKmDriven(int kmDriven) { this.kmDriven = kmDriven; }
    public boolean isSelfDriving() { return selfDriving; }
    public void setSelfDriving(boolean selfDriving) { this.selfDriving = selfDriving; }
    public boolean isWithDriver() { return withDriver; }
    public void setWithDriver(boolean withDriver) { this.withDriver = withDriver; }
    public com.example.FullCarSystem.Modules.model.RentalVehicle.Status getStatus() { return status; }
    public void setStatus(com.example.FullCarSystem.Modules.model.RentalVehicle.Status status) { this.status = status; }
    public boolean isPriceNegotiable() { return priceNegotiable; }
    public void setPriceNegotiable(boolean priceNegotiable) { this.priceNegotiable = priceNegotiable; }
    public java.util.List<String> getFeatures() { return features; }
    public void setFeatures(java.util.List<String> features) { this.features = features; }
    public java.util.List<String> getVehicleDocuments() { return vehicleDocuments; }
    public void setVehicleDocuments(java.util.List<String> vehicleDocuments) { this.vehicleDocuments = vehicleDocuments; }
    public java.util.List<RatingDTO> getRatings() { return ratings; }
    public void setRatings(java.util.List<RatingDTO> ratings) { this.ratings = ratings; }
    public java.util.List<String> getImages() { return images; }
    public void setImages(java.util.List<String> images) { this.images = images; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getAvailabilityStartDate() { return availabilityStartDate; }
    public void setAvailabilityStartDate(String availabilityStartDate) { this.availabilityStartDate = availabilityStartDate; }
    public String getAvailabilityEndDate() { return availabilityEndDate; }
    public void setAvailabilityEndDate(String availabilityEndDate) { this.availabilityEndDate = availabilityEndDate; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
} 