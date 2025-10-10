package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PartUpdateController {

    private final PartRepository partRepository;

    public PartUpdateController(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    /**
     * Used by the "Update" button in the Parts table.
     * It detects the concrete type and forwards to the correct Thymeleaf form
     * with the exact model attribute name those forms expect.
     */
    @GetMapping("/showPartFormForUpdate")
    public String showPartFormForUpdate(@RequestParam("partID") Long id, Model model) {
        Part p = partRepository.findById(id).orElse(null);
        if (p == null) {
            return "redirect:/mainscreen";
        }

        if (p instanceof InhousePart) {
            model.addAttribute("inhousepart", (InhousePart) p); // MUST be "inhousepart"
            return "InhousePartForm";                           // MUST match template filename
        } else if (p instanceof OutsourcedPart) {
            model.addAttribute("outsourcedpart", (OutsourcedPart) p); // MUST be "outsourcedpart"
            return "OutsourcedPartForm";                               // MUST match template filename
        }

        // Fallback (shouldn't happen if only two subtypes exist)
        return "redirect:/mainscreen";
    }
}

