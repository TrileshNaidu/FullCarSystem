package com.example.FullCarSystem.Modules.controller;

import com.example.FullCarSystem.Modules.DTO.ListingDTO;
import com.example.FullCarSystem.Modules.Service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
    @Autowired
    private ListingService listingService;

    @GetMapping
    public List<ListingDTO> getAllListings() {
        return listingService.getAllListings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDTO> getListingById(@PathVariable Long id) {
        ListingDTO dto = listingService.getListingById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ListingDTO createListing(@RequestBody ListingDTO dto) {
        return listingService.createListing(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingDTO> updateListing(@PathVariable Long id, @RequestBody ListingDTO dto, Authentication authentication) {
        ListingDTO existing = listingService.getListingById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or owner
        if (!isAdmin && (existing.getSellerId() == null || !existing.getSellerId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        ListingDTO updated = listingService.updateListing(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id, Authentication authentication) {
        ListingDTO existing = listingService.getListingById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String username = authentication.getName();
        // Only allow if admin or owner
        if (!isAdmin && (existing.getSellerId() == null || !existing.getSellerId().toString().equals(username))) {
            return ResponseEntity.status(403).build();
        }
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadListingImageById(@PathVariable Long id, @RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images" + File.separator + "listings" + File.separator + id + File.separator;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        // Add image path to listing
        ListingDTO listing = listingService.getListingById(id);
        if (listing != null) {
            if (listing.getImages() == null) listing.setImages(new ArrayList<>());
            listing.getImages().add(filePath);
            updateListing(id, listing, authentication);
        }
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/{id}/uploadDocument")
    public ResponseEntity<String> uploadListingDocumentById(@PathVariable Long id, @RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
        }
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only PDF documents are allowed");
        }
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "docs" + File.separator + "listings" + File.separator + id + File.separator;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        // Add document path to listing
        ListingDTO listing = listingService.getListingById(id);
        if (listing != null) {
            if (listing.getVehicleDocuments() == null) listing.setVehicleDocuments(new ArrayList<>());
            listing.getVehicleDocuments().add(filePath);
            updateListing(id, listing, authentication);
        }
        return ResponseEntity.ok(filePath);
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<Void> addListingRating(@PathVariable Long id, @RequestBody com.example.FullCarSystem.Modules.DTO.RatingDTO rating) {
        ListingDTO listing = getListingById(id).getBody();
        if (listing == null) return ResponseEntity.notFound().build();
        if (listing.getRatings() == null) listing.setRatings(new ArrayList<>());
        listing.getRatings().add(rating);
        updateListing(id, listing, null); // No authentication for this call
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<java.util.List<com.example.FullCarSystem.Modules.DTO.RatingDTO>> getListingRatings(@PathVariable Long id) {
        ListingDTO listing = getListingById(id).getBody();
        if (listing == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listing.getRatings());
    }

    @GetMapping("/type/{listingType}")
    public List<ListingDTO> getListingsByType(@PathVariable String listingType) {
        return listingService.getListingsByListingType(listingType);
    }
} 