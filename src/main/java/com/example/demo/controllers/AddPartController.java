package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AddPartController {

    private final PartRepository partRepository;

    public AddPartController(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    // Show forms
    @GetMapping("/showFormAddInPart")
    public String showFormAddInPart(Model model) {
        InhousePart p = new InhousePart();
        p.setMin(2); p.setMax(10); p.setInv(2);
        model.addAttribute("inhousepart", p);
        return "InhousePartForm";
    }

    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutPart(Model model) {
        OutsourcedPart p = new OutsourcedPart();
        p.setMin(2); p.setMax(10); p.setInv(2);
        model.addAttribute("outsourcedpart", p);
        return "OutsourcedPartForm";
    }

    // Update
    @GetMapping("/showPartFormForUpdate")
    public String showPartFormForUpdate(@RequestParam("partID") long id, Model model) {
        Optional<Part> opt = partRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/mainscreen";
        Part p = opt.get();
        if (p instanceof InhousePart) {
            model.addAttribute("inhousepart", p);
            return "InhousePartForm";
        } else {
            model.addAttribute("outsourcedpart", p);
            return "OutsourcedPartForm";
        }
    }

    // Save inhouse
    @PostMapping("/saveInhousePart")
    public String saveInhousePart(@Valid @ModelAttribute("inhousepart") InhousePart part,
                                  BindingResult result, RedirectAttributes ra) {

        validateMinMax(part, result);
        if (result.hasErrors()) return "InhousePartForm";

        partRepository.save(part);
        ra.addFlashAttribute("message", "In-house part saved.");
        return "redirect:/mainscreen";
    }

    // Save outsourced
    @PostMapping("/saveOutsourcedPart")
    public String saveOutsourcedPart(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part,
                                     BindingResult result, RedirectAttributes ra) {

        validateMinMax(part, result);
        if (result.hasErrors()) return "OutsourcedPartForm";

        partRepository.save(part);
        ra.addFlashAttribute("message", "Outsourced part saved.");
        return "redirect:/mainscreen";
    }

    // Delete
    @GetMapping("/deletepart")
    public String deletePart(@RequestParam("partID") long id, RedirectAttributes ra) {
        if (partRepository.existsById(id)) {
            partRepository.deleteById(id);
            ra.addFlashAttribute("message", "Part deleted.");
        } else {
            ra.addFlashAttribute("message", "Part not found.");
        }
        return "redirect:/mainscreen";
    }

    // Validation for Part H
    private void validateMinMax(Part part, BindingResult result) {
        if (part.getMax() < part.getMin()) {
            result.rejectValue("max", "maxLessThanMin", "Max must be ≥ Min.");
        }
        if (part.getInv() < part.getMin()) {
            result.rejectValue("inv", "invBelowMin", "Inventory cannot be less than the minimum.");
        }
        if (part.getInv() > part.getMax()) {
            result.rejectValue("inv", "invAboveMax", "Inventory cannot exceed the maximum.");
        }
        if (part.getPrice() < 0) {
            result.rejectValue("price", "priceNegative", "Price must be ≥ 0");
        }
    }
}

