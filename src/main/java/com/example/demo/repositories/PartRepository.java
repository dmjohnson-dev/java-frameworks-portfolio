package com.example.demo.repositories;

import com.example.demo.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByNameContainingIgnoreCase(String name);
}


