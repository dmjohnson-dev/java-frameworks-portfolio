package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.repositories.PartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AddInhousePartController {

    private final PartRepository partRepository;

    public AddInhousePartController(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    // Show form to add a new In-house part
    @GetMapping("/showFormAddInPart")
    public String showFormAddInPart(Model model) {
        model.addAttribute("inhousepart", new InhousePart()); // must match th:object
        return "InhousePartForm"; // must match templates/InhousePartForm.html (case-sensitive)
    }

    // Save in-house part (create or update)
    @PostMapping("/saveInhousePart")
    public String saveInhousePart(@Valid @ModelAttribute("inhousepart") InhousePart part,
                                  BindingResult br,
                                  RedirectAttributes ra) {

        // Basic min/max validations (G/H)
        if (part.getMin() > part.getMax()) {
            br.rejectValue("min", "min.gt.max", "Minimum cannot be greater than maximum.");
        }
        if (part.getInv() < part.getMin()) {
            br.rejectValue("inv", "inv.lt.min", "Inventory cannot be less than minimum.");
        }
        if (part.getInv() > part.getMax()) {
            br.rejectValue("inv", "inv.gt.max", "Inventory cannot exceed maximum.");
        }

        if (br.hasErrors()) {
            return "InhousePartForm";
        }

        partRepository.save(part);
        ra.addFlashAttribute("message", "In-house part saved.");
        return "redirect:/mainscreen";
    }
}

