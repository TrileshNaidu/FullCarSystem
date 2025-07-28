package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobileNumber")
})
//done
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 2, max = 50)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank @Size(min = 8, max = 100)
    @Column(nullable = false)
    private String password;

    @NotBlank @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank @Pattern(regexp = "^[6-9]\\d{9}$")
    @Column(name = "mobile_number", nullable = false, unique = true)  // Explicit column mapping
    private String mobileNumber;

    // Rental relationships (as car owner)
    @OneToMany(mappedBy = "carOwner", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Rental> ownedRentals = new ArrayList<>();

    // Rental relationships (as renter)
    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Rental> rentedCars = new ArrayList<>();

    // Roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    // Wishlist
    @ManyToMany
    @JoinTable(
            name = "user_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "listing_id")
    )
    @Builder.Default
    private Set<Listing> wishlistedItems = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_seller_ratings", joinColumns = @JoinColumn(name = "user_id"))
    private List<Rating> sellerRatings = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_buyer_ratings", joinColumns = @JoinColumn(name = "user_id"))
    private List<Rating> buyerRatings = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public String getMainRoleName() {
        return roles.stream()
            .map(role -> role.getName().name())
            .findFirst()
            .orElse("ROLE_USER");
    }


}