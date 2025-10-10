package com.example.demo.repositories;

import com.example.demo.domain.Part;
import org.springframework.data.repository.CrudRepository;

public interface PartRepository extends CrudRepository<Part, Long> {
}
