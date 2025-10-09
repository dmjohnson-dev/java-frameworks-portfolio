package com.example.demo.controllers;

import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AddProductController {

    private final ProductRepository productRepo;

    public AddProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * "Buy Now" endpoint:
     * - Decrements ONLY the product inventory by 1.
     * - Does NOT touch any associated parts.
     * - Adds a flash message indicating success or failure, then redirects to /mainscreen.
     */
    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productID") long id,
                             RedirectAttributes redirect) {

        Optional<Product> opt = productRepo.findById(id);
        if (!opt.isPresent()) {
            redirect.addFlashAttribute("messageType", "danger");
            redirect.addFlashAttribute("message", "Purchase failed: product not found.");
            return "redirect:/mainscreen";
        }

        Product p = opt.get();
        if (p.getInv() > 0) {
            p.setInv(p.getInv() - 1);
            productRepo.save(p);
            redirect.addFlashAttribute("messageType", "success");
            redirect.addFlashAttribute(
                    "message",
                    "Purchase successful: " + p.getName() + " (remaining: " + p.getInv() + ")"
            );
        } else {
            redirect.addFlashAttribute("messageType", "warning");
            redirect.addFlashAttribute(
                    "message",
                    "Purchase failed: " + p.getName() + " is out of stock."
            );
        }
        return "redirect:/mainscreen";
    }
}
