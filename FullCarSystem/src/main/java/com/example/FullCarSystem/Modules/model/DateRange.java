package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {
    private LocalDate start;
    private LocalDate endDate;
} 