package com.example.FullCarSystem.Modules.controller;

import com.example.FullCarSystem.DTO.SignupRequest;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import com.example.FullCarSystem.Modules.model.Rental;
import com.example.FullCarSystem.Modules.model.User;
import com.example.FullCarSystem.Modules.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.createUser(signupRequest));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/rentals")
    public ResponseEntity<List<Rental>> getUserRentals(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserRentals(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user, Authentication authentication) {
        // Only allow if admin or the user themselves
        // If not admin, check if the authenticated user's id matches the path id
        if (!authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            String username = authentication.getName();
            // Fetch the user by username and check id
            // (Assume userService has a method to get user by username)
            User currentUser = userService.getUserByUsername(username);
            if (!currentUser.getId().equals(id)) {
                return ResponseEntity.status(403).build();
            }
        }
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ratings/seller")
    public ResponseEntity<Void> addSellerRating(@PathVariable Long id, @RequestBody RatingDTO rating) {
        userService.addSellerRating(id, rating);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/ratings/buyer")
    public ResponseEntity<Void> addBuyerRating(@PathVariable Long id, @RequestBody RatingDTO rating) {
        userService.addBuyerRating(id, rating);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/ratings/seller")
    public ResponseEntity<List<RatingDTO>> getSellerRatings(@PathVariable Long id) {
        User user = userService.getUserById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<RatingDTO> ratings = user.getSellerRatings().stream()
            .map(r -> new RatingDTO(r.getScore(), r.getReview()))
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{id}/ratings/buyer")
    public ResponseEntity<List<RatingDTO>> getBuyerRatings(@PathVariable Long id) {
        User user = userService.getUserById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<RatingDTO> ratings = user.getBuyerRatings().stream()
            .map(r -> new RatingDTO(r.getScore(), r.getReview()))
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ratings);
    }
}