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
import java.util.List;

@Controller
public class MainScreenController {

    private final PartRepository partRepo;
    private final ProductRepository productRepo;

    public MainScreenController(PartRepository partRepo, ProductRepository productRepo) {
        this.partRepo = partRepo;
        this.productRepo = productRepo;
    }

    @GetMapping({"/", "/mainscreen"})
    public String showMain(@RequestParam(required = false) String partkeyword,
                           @RequestParam(required = false) String productkeyword,
                           Model model) {

        // Convert Iterable -> List
        List<Part> parts = new ArrayList<>();
        partRepo.findAll().forEach(parts::add);

        List<Product> products = new ArrayList<>();
        productRepo.findAll().forEach(products::add);

        model.addAttribute("parts", parts);
        model.addAttribute("products", products);
        model.addAttribute("partkeyword", partkeyword);
        model.addAttribute("productkeyword", productkeyword);

        System.out.println("UI model -> parts=" + parts.size() + ", products=" + products.size());
        return "mainscreen";
    }
}
