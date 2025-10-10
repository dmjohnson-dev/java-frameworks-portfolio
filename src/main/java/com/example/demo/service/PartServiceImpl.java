package com.example.demo.service;

import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override public List<Part> findAll() { return partRepository.findAll(); }

    @Override public Part findById(Long id) {
        return partRepository.findById(id).orElse(null);
    }

    @Override public Part save(Part part) { return partRepository.save(part); }

    @Override public void deleteById(Long id) { partRepository.deleteById(id); }

    @Override public List<Part> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) return partRepository.findAll();
        return partRepository.findByNameContainingIgnoreCase(keyword);
    }
}

