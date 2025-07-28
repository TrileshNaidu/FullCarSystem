package com.example.FullCarSystem.Modules.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private int score;
    private String review;
    private LocalDateTime date = LocalDateTime.now();
} 