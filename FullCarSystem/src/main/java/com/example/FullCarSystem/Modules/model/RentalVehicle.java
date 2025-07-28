package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.example.FullCarSystem.Modules.model.DateRange;

@Entity
@Table(name = "rental_vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private int year;
    @Enumerated(EnumType.STRING)
    private CarType type;
    private String color;
    private int mileage;
    private double dailyRate;
    private boolean available;
    private int kmDriven;
    private boolean selfDriving;
    private boolean withDriver;
    private String ownerName;
    private String vehicleNumber;
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    private Long ownerId;
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    @Column(nullable = false)
    private boolean priceNegotiable = false;
    private java.util.List<String> features;
    private java.util.List<String> vehicleDocuments;
    private java.util.List<String> images;
    private String location;
    private String contactNumber;
    private String availabilityStartDate;
    private String availabilityEndDate;
    @ElementCollection
    @CollectionTable(name = "rental_vehicle_ratings", joinColumns = @JoinColumn(name = "rental_vehicle_id"))
    private List<Rating> ratings = new java.util.ArrayList<>();
    public enum Status {
        PENDING, APPROVED, REJECTED
    }
    @Enumerated(EnumType.STRING)
    private Status status;
} 