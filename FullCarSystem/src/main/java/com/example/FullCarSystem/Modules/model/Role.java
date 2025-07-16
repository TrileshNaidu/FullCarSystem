package com.example.FullCarSystem.Modules.model;


import jakarta.persistence.*;
import lombok.Data;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;
}