package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Listing {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinColumn(name = "owner_id")
    private User owner;


}
