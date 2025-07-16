package com.example.FullCarSystem.core.config;


import com.example.FullCarSystem.Modules.model.Role;
import com.example.FullCarSystem.Modules.model.RoleType;
import com.example.FullCarSystem.Modules.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        // Check and create roles if they don't exist
        for (RoleType roleType : RoleType.values()) {
            if (!roleRepository.existsByName(roleType)) {
                Role role = new Role();
                role.setName(roleType);
                roleRepository.save(role);
            }
        }
    }
}