package com.example.FullCarSystem.Modules.repository;

import com.example.FullCarSystem.Modules.model.MakeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MakeModelRepository extends JpaRepository<MakeModel, Long> {
    Optional<MakeModel> findById(Long id);
    List<MakeModel> findByMake(String make);
} 