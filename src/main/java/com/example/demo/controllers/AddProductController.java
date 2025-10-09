package com.example.demo.controllers;

import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AddProductController {

    private final ProductRepository productRepository;

    public AddProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ===== ADD FORM =====
    @GetMapping("/showFormAddProduct")
    public String showFormAddProduct(Model model) {
        model.addAttribute("product", new Product());
        return "ProductForm"; // template must exist at templates/ProductForm.html
    }

    // ===== EDIT FORM =====
    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") long id, Model model) {
        Optional<Product> opt = productRepository.findById(id);
        if (!opt.isPresent()) {
            return "redirect:/mainscreen";
        }
        model.addAttribute("product", opt.get());
        return "ProductForm";
    }

    // ===== SAVE (CREATE/UPDATE) =====
    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              RedirectAttributes ra) {

        if (product.getPrice() < 0) {
            result.rejectValue("price", "priceNegative", "Price must be ≥ 0");
        }
        if (product.getInv() < 0) {
            result.rejectValue("inv", "invNegative", "Inventory must be ≥ 0");
        }

        if (result.hasErrors()) {
            return "ProductForm";
        }

        productRepository.save(product);
        ra.addFlashAttribute("message", "Product saved.");
        return "redirect:/mainscreen";
    }

    // ===== DELETE =====
    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") long id,
                                RedirectAttributes ra) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            ra.addFlashAttribute("message", "Product deleted.");
        } else {
            ra.addFlashAttribute("message", "Product not found.");
        }
        return "redirect:/mainscreen";
    }

    // ===== PART F: BUY NOW =====
    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productID") long id,
                             RedirectAttributes ra) {
        Optional<Product> opt = productRepository.findById(id);
        if (!opt.isPresent()) {
            ra.addFlashAttribute("message", "Purchase failed: product not found.");
            return "redirect:/mainscreen";
        }

        Product p = opt.get();
        if (p.getInv() <= 0) {
            ra.addFlashAttribute("message", "Purchase failed: out of stock.");
            return "redirect:/mainscreen";
        }

        p.setInv(p.getInv() - 1);      // decrement product inventory ONLY
        productRepository.save(p);
        ra.addFlashAttribute("message", "Purchase successful! Remaining inventory: " + p.getInv());
        return "redirect:/mainscreen";
    }
}

