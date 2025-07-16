package com.example.FullCarSystem.Modules.model;


public enum RoleType {
    ROLE_ADMIN("System Administrator"),
    ROLE_RENTER("Car Renter"),
    ROLE_SELLER("Vehicle Seller"),
    ROLE_BUYER("Vehicle Buyer"),
    ROLE_USER("Basic User");  // Added a default role

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}