package com.example.demo.service;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface InhousePartService {
    List<InhousePart> findAll();
    InhousePart findById(int theId);
    void save(InhousePart thePart);
    void deleteById(int theId);
}
