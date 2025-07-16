package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other fields...

    // Corrected relationship mapping
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // If you need a many-to-many for wishlist
    @ManyToMany(mappedBy = "wishlistedItems")
    private Set<User> interestedUsers;
}