package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_task")
public class DeliveryTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public enum DeliveryStatus {
        PENDING, IN_PROGRESS, DELIVERED, FAILED
    }
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // e.g., PENDING, IN_PROGRESS, DELIVERED, FAILED
    private LocalDateTime scheduledTime;
    private String driverName;
    private String driverContact;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    // Constructors, getters, setters
    public DeliveryTask() {}

    public DeliveryTask(Booking booking, DeliveryStatus deliveryStatus, LocalDateTime scheduledTime, String driverName, String driverContact) {
        this.booking = booking;
        this.deliveryStatus = deliveryStatus;
        this.scheduledTime = scheduledTime;
        this.driverName = driverName;
        this.driverContact = driverContact;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public DeliveryStatus getDeliveryStatus() { return deliveryStatus; }
    public void setDeliveryStatus(DeliveryStatus deliveryStatus) { this.deliveryStatus = deliveryStatus; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDateTime scheduledTime) { this.scheduledTime = scheduledTime; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public String getDriverContact() { return driverContact; }
    public void setDriverContact(String driverContact) { this.driverContact = driverContact; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
} 