package com.example.demo.controllers;

import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AddProductController {

    private final ProductRepository productRepository;

    public AddProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ADD
    @GetMapping("/showFormAddProduct")
    public String showFormAddProduct(Model model) {
        model.addAttribute("product", new Product());   // model name MUST be "product"
        return "ProductForm";                           // template MUST be "ProductForm.html"
    }

    // UPDATE
    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/mainscreen";
        }
        model.addAttribute("product", product);         // same model name
        return "ProductForm";                           // same template
    }

    // SAVE (add + update)
    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult br,
                              RedirectAttributes ra) {
        if (product.getPrice() < 0) {
            br.rejectValue("price", "price.lt.zero", "Price must be non-negative.");
        }
        if (product.getInv() < 0) {
            br.rejectValue("inv", "inv.lt.zero", "Inventory must be non-negative.");
        }
        if (br.hasErrors()) {
            return "ProductForm";
        }
        productRepository.save(product);
        ra.addFlashAttribute("message", "Product saved.");
        return "redirect:/mainscreen";
    }

    // BUY NOW
    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productID") Long id, RedirectAttributes ra) {
        Product p = productRepository.findById(id).orElse(null);
        if (p == null) {
            ra.addFlashAttribute("message", "Purchase failed: product not found.");
            return "redirect:/mainscreen";
        }
        if (p.getInv() <= 0) {
            ra.addFlashAttribute("message", "Purchase failed: out of stock.");
            return "redirect:/mainscreen";
        }
        p.setInv(p.getInv() - 1);
        productRepository.save(p);
        ra.addFlashAttribute("message", "Purchase successful.");
        return "redirect:/mainscreen";
    }
}

