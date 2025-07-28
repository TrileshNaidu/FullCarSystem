package com.example.FullCarSystem.Modules.DTO;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String mobileNumber;
    private List<RatingDTO> sellerRatings;
    private List<RatingDTO> buyerRatings;

    public UserDTO() {}
    public UserDTO(Long id, String username, String email, String mobileNumber, List<RatingDTO> sellerRatings, List<RatingDTO> buyerRatings) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.sellerRatings = sellerRatings;
        this.buyerRatings = buyerRatings;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public List<RatingDTO> getSellerRatings() { return sellerRatings; }
    public void setSellerRatings(List<RatingDTO> sellerRatings) { this.sellerRatings = sellerRatings; }
    public List<RatingDTO> getBuyerRatings() { return buyerRatings; }
    public void setBuyerRatings(List<RatingDTO> buyerRatings) { this.buyerRatings = buyerRatings; }
} 