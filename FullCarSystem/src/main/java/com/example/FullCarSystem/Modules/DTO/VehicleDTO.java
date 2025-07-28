package com.example.FullCarSystem.Modules.DTO;

import com.example.FullCarSystem.Modules.model.CarType;
import jakarta.validation.constraints.*;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;

public class VehicleDTO {
    private Long id;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @Min(value = 1886, message = "Year must be valid")
    private int year;

    @NotNull(message = "Type is required")
    private CarType type;

    @NotBlank(message = "Color is required")
    private String color;

    @Min(value = 0, message = "Mileage must be non-negative")
    private int mileage;

    @Min(value = 0, message = "kmDriven must be non-negative")
    private int kmDriven;

    private boolean priceNegotiable;
    private java.util.List<String> features;
    private java.util.List<String> vehicleDocuments; // URLs or file paths to PDFs
    private java.util.List<RatingDTO> ratings; // Use RatingDTO for ratings
    private java.util.List<String> images; // URLs or file paths to images
    private String location;
    private String contactNumber;
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
    public int getKmDriven() { return kmDriven; }
    public void setKmDriven(int kmDriven) { this.kmDriven = kmDriven; }

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
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
} 