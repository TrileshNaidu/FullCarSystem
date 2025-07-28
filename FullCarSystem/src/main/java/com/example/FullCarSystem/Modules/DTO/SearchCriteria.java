package com.example.FullCarSystem.Modules.DTO;

import com.example.FullCarSystem.Modules.model.CarType;

public class SearchCriteria {
    private String make;
    private String model;
    private Integer yearFrom;
    private Integer yearTo;
    private CarType type;
    private String color;
    private Integer mileageMax;
    private Double priceMin;
    private Double priceMax;

    // Getters and setters
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getYearFrom() { return yearFrom; }
    public void setYearFrom(Integer yearFrom) { this.yearFrom = yearFrom; }
    public Integer getYearTo() { return yearTo; }
    public void setYearTo(Integer yearTo) { this.yearTo = yearTo; }
    public CarType getType() { return type; }
    public void setType(CarType type) { this.type = type; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Integer getMileageMax() { return mileageMax; }
    public void setMileageMax(Integer mileageMax) { this.mileageMax = mileageMax; }
    public Double getPriceMin() { return priceMin; }
    public void setPriceMin(Double priceMin) { this.priceMin = priceMin; }
    public Double getPriceMax() { return priceMax; }
    public void setPriceMax(Double priceMax) { this.priceMax = priceMax; }
} 