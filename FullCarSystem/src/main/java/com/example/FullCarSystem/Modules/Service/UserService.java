package com.example.FullCarSystem.Modules.Service;

import com.example.FullCarSystem.DTO.SignupRequest;
import com.example.FullCarSystem.Modules.DTO.RatingDTO;
import com.example.FullCarSystem.Modules.model.*;
import com.example.FullCarSystem.Modules.repository.RoleRepository;
import com.example.FullCarSystem.Modules.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .mobileNumber(signupRequest.getMobileNumber())
                .build();

        // Assign default role
        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setMobileNumber(updatedUser.getMobileNumber());
                    if (updatedUser.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        // Check if user has active rentals before deleting
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOwnedRentals().isEmpty() || !user.getRentedCars().isEmpty()) {
            throw new RuntimeException("Cannot delete user with active rentals");
        }

        userRepository.deleteById(id);
    }

    public List<Rental> getUserRentals(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Rental> allRentals = new ArrayList<>();
        allRentals.addAll(user.getOwnedRentals());
        allRentals.addAll(user.getRentedCars());

        return allRentals;
    }

    public void addSellerRating(Long userId, RatingDTO ratingDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getSellerRatings() == null) user.setSellerRatings(new java.util.ArrayList<>());
        user.getSellerRatings().add(new Rating(ratingDTO.getScore(), ratingDTO.getReview(), LocalDateTime.now()));
        userRepository.save(user);
    }

    public void addBuyerRating(Long userId, RatingDTO ratingDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getBuyerRatings() == null) user.setBuyerRatings(new java.util.ArrayList<>());
        user.getBuyerRatings().add(new Rating(ratingDTO.getScore(), ratingDTO.getReview(), LocalDateTime.now()));
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}