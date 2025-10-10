package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Controller
public class MainScreenController {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    public MainScreenController(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    @GetMapping({"/", "/mainscreen"})
    public String main(@RequestParam(value = "partkeyword", required = false) String partkeyword,
                       @RequestParam(value = "productkeyword", required = false) String productkeyword,
                       Model model) {

        // Iterable -> List (works with CrudRepository)
        List<Part> parts = new ArrayList<>();
        for (Part p : partRepository.findAll()) parts.add(p);
        parts.sort(Comparator.comparing(Part::getName, String.CASE_INSENSITIVE_ORDER));

        List<Product> products = new ArrayList<>();
        for (Product pr : productRepository.findAll()) products.add(pr);
        products.sort(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER));

        // Filter
        if (partkeyword != null && !partkeyword.isBlank()) {
            String k = partkeyword.toLowerCase(Locale.ROOT);
            List<Part> filtered = new ArrayList<>();
            for (Part p : parts) {
                if (p.getName() != null && p.getName().toLowerCase(Locale.ROOT).contains(k)) filtered.add(p);
            }
            parts = filtered;
        }
        if (productkeyword != null && !productkeyword.isBlank()) {
            String k = productkeyword.toLowerCase(Locale.ROOT);
            List<Product> filtered = new ArrayList<>();
            for (Product pr : products) {
                if (pr.getName() != null && pr.getName().toLowerCase(Locale.ROOT).contains(k)) filtered.add(pr);
            }
            products = filtered;
        }

        model.addAttribute("parts", parts);
        model.addAttribute("products", products);
        model.addAttribute("partkeyword", partkeyword);
        model.addAttribute("productkeyword", productkeyword);
        return "mainScreen";
    }
}
