package com.example.demo.service;

import com.example.demo.domain.Part;
import java.util.List;

public interface PartService {
    List<Part> findAll();
    Part findById(Long id);
    Part save(Part part);
    void deleteById(Long id);
    List<Part> searchByName(String keyword);
}

