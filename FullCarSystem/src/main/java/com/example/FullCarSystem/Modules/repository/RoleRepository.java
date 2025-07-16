package com.example.FullCarSystem.Modules.repository;

import com.example.FullCarSystem.Modules.model.Role;
import com.example.FullCarSystem.Modules.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
    boolean existsByName(RoleType name);  // Add this line

}