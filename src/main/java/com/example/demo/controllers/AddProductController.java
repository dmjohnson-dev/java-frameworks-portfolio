package com.example.demo.controllers;

import com.example.demo.domain.Part;
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

    @GetMapping("/showFormAddProduct")
    public String showFormAddProduct(Model model) {
        model.addAttribute("product", new Product());
        return "ProductForm";
    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") long id, Model model) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/mainscreen";
        model.addAttribute("product", opt.get());
        return "ProductForm";
    }

    // Save Product with Part H validation (product lowering parts below min)
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
        if (result.hasErrors()) return "ProductForm";

        // Compute delta in product inv to see if parts would be over-used
        int delta;
        if (product.getId() == 0) {
            delta = product.getInv();
        } else {
            int currentInv = productRepository.findById(product.getId())
                    .map(Product::getInv).orElse(0);
            delta = product.getInv() - currentInv;
        }

        // Simple 1:1 parts consumption rule
        if (delta > 0 && product.getParts() != null && !product.getParts().isEmpty()) {
            for (Part p : product.getParts()) {
                int projectedPartInv = p.getInv() - delta;
                if (projectedPartInv < p.getMin()) {
                    result.reject("partInvBelowMin",
                            "Cannot set Product inventory to " + product.getInv()
                                    + ": Part '" + p.getName()
                                    + "' would drop below its minimum (" + p.getMin() + ").");
                    break;
                }
            }
        }

        if (result.hasErrors()) return "ProductForm";

        productRepository.save(product);
        ra.addFlashAttribute("message", "Product saved.");
        return "redirect:/mainscreen";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") long id, RedirectAttributes ra) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            ra.addFlashAttribute("message", "Product deleted.");
        } else {
            ra.addFlashAttribute("message", "Product not found.");
        }
        return "redirect:/mainscreen";
    }

    // Part F: Buy Now (decrement only product inv, not parts)
    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productID") long id, RedirectAttributes ra) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("message", "Purchase failed: product not found.");
            return "redirect:/mainscreen";
        }
        Product p = opt.get();
        if (p.getInv() <= 0) {
            ra.addFlashAttribute("message", "Purchase failed: out of stock.");
            return "redirect:/mainscreen";
        }
        p.setInv(p.getInv() - 1);
        productRepository.save(p);
        ra.addFlashAttribute("message", "Purchase successful! Remaining inventory: " + p.getInv());
        return "redirect:/mainscreen";
    }
}
