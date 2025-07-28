package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "listing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleNumber;
    private String ownerName;
    private Long sellerId; // Now nullable
    private double price;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private boolean priceNegotiable = false;
    private java.util.List<String> features;
    private java.util.List<String> vehicleDocuments;
    private java.util.List<String> images;
    private String location;
    private String contactNumber;
    private String listingType; // 'RENTAL' or 'SALE'
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
    @ElementCollection
    @CollectionTable(name = "listing_ratings", joinColumns = @JoinColumn(name = "listing_id"))
    private List<Rating> ratings = new java.util.ArrayList<>();
}