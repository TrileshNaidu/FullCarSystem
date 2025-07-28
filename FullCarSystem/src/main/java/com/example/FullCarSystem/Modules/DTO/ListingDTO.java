package com.example.FullCarSystem.Modules.DTO;

import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import java.time.LocalDateTime;

public class ListingDTO {
    private Long id;
    private String vehicleNumber;
    private String ownerName;
    private String make;
    private String model;
    private int year;
    private String type;
    private String color;
    private int mileage;
    private double dailyRate;
    private boolean available;
    private int kmDriven;
    private boolean selfDriving;
    private boolean withDriver;
    private String availabilityStartDate;
    private String availabilityEndDate;
    private Long sellerId;
    private double price;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private boolean priceNegotiable;
    private java.util.List<String> features;
    private java.util.List<String> vehicleDocuments; // URLs or file paths to PDFs
    private java.util.List<RatingDTO> ratings; // Use RatingDTO for ratings
    private java.util.List<String> images; // URLs or file paths to images
    private String location;
    private String contactNumber;
    private String listingType; // 'RENTAL' or 'SALE'

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
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
    public String getAvailabilityStartDate() { return availabilityStartDate; }
    public void setAvailabilityStartDate(String availabilityStartDate) { this.availabilityStartDate = availabilityStartDate; }
    public String getAvailabilityEndDate() { return availabilityEndDate; }
    public void setAvailabilityEndDate(String availabilityEndDate) { this.availabilityEndDate = availabilityEndDate; }
    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
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
    public String getListingType() { return listingType; }
    public void setListingType(String listingType) { this.listingType = listingType; }
} 