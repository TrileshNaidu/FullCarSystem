package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;

@Entity
@Table(name = "make_model")
public class MakeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;

    // Constructors, getters, setters
    public MakeModel() {}

    public MakeModel(String make, String model) {
        this.make = make;
        this.model = model;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
} 