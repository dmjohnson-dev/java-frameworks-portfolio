package com.example.demo.service;

import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface OutsourcedPartService {
        List<OutsourcedPart> findAll();
        OutsourcedPart findById(int theId);
        void save(OutsourcedPart thePart);
        void deleteById(int theId);
}
