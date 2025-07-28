package com.example.FullCarSystem.core.config;


import com.example.FullCarSystem.Modules.model.Role;
import com.example.FullCarSystem.Modules.model.RoleType;
import com.example.FullCarSystem.Modules.model.User;
import com.example.FullCarSystem.Modules.repository.RoleRepository;
import com.example.FullCarSystem.Modules.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        // Create default admin user if not exists
        String adminUsername = "trilesh";
        if (!userRepository.existsByUsername(adminUsername)) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
            User admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode("trilesh"))
                .email("trileshnaidukatreddy@gmail.com")
                .mobileNumber("9492973470")
                .roles(Set.of(adminRole))
                .build();
            userRepository.save(admin);
        }
    }
}

