package com.example.FullCarSystem.Modules.DTO;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeDTO {
    private LocalDate start;
    private LocalDate endDate;
} 