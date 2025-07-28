package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
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
    private Integer kmDriven;
    private String vehicleNumber;
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    private Long ownerId;
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean priceNegotiable;
    private java.util.List<String> features;
    private java.util.List<String> vehicleDocuments;
    private java.util.List<String> images;
    private String location;
    private String contactNumber;
    @ElementCollection
    @CollectionTable(name = "vehicle_ratings", joinColumns = @JoinColumn(name = "vehicle_id"))
    private List<Rating> ratings = new java.util.ArrayList<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 