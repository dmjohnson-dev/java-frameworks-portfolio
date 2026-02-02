package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DeleteController {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    public DeleteController(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    /** Delete PART by id, detach from all products first */
    @Transactional
    @GetMapping("/deletepart")
    public String deletePart(@RequestParam("partID") Long id, RedirectAttributes ra) {
        return partRepository.findById(id).map(part -> {
            // Detach from all related products (Many-to-Many cleanup)
            for (Product p : part.getProducts()) {
                p.getParts().remove(part);
            }
            part.getProducts().clear();

            partRepository.delete(part);
            ra.addFlashAttribute("message", "Part deleted.");
            return "redirect:/mainscreen";
        }).orElseGet(() -> {
            ra.addFlashAttribute("message", "Part not found.");
            return "redirect:/mainscreen";
        });
    }

    /** Delete PRODUCT by id, detach from all parts first */
    @Transactional
    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") Long id, RedirectAttributes ra) {
        return productRepository.findById(id).map(product -> {
            // Detach from all related parts (Many-to-Many cleanup)
            for (Part part : product.getParts()) {
                part.getProducts().remove(product);
            }
            product.getParts().clear();

            productRepository.delete(product);
            ra.addFlashAttribute("message", "Product deleted.");
            return "redirect:/mainscreen";
        }).orElseGet(() -> {
            ra.addFlashAttribute("message", "Product not found.");
            return "redirect:/mainscreen";
        });
    }
}
