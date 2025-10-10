package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainScreenController {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    public MainScreenController(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    @GetMapping({"/", "/mainscreen"})
    public String showMain(
            @RequestParam(value = "partkeyword", defaultValue = "") String partkeyword,
            @RequestParam(value = "productkeyword", defaultValue = "") String productkeyword,
            Model model) {

        // parts list
        final List<Part> parts = partkeyword.isBlank()
                ? partRepository.findAll()
                : partRepository.findByNameContainingIgnoreCase(partkeyword);

        // products list
        final List<Product> products = productkeyword.isBlank()
                ? productRepository.findAll()
                : productRepository.findByNameContainingIgnoreCase(productkeyword);

        model.addAttribute("parts", parts);
        model.addAttribute("products", products);
        model.addAttribute("partkeyword", partkeyword);
        model.addAttribute("productkeyword", productkeyword);

        // IMPORTANT: must match src/main/resources/templates/mainscreen.html
        return "mainscreen";
    }
}
