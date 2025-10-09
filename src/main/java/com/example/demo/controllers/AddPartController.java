package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AddPartController {

    private final PartRepository partRepository;

    public AddPartController(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    // -------- Create forms --------

    @GetMapping("/showFormAddInPart")
    public String showFormAddInPart(Model model) {
        model.addAttribute("inhousePart", new InhousePart());
        return "InhousePartForm";
    }

    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutPart(Model model) {
        model.addAttribute("outsourcedPart", new OutsourcedPart());
        return "OutsourcedPartForm";
    }

    // -------- Update form (route used by mainscreen) --------

    @GetMapping("/showPartFormForUpdate")
    public String showPartFormForUpdate(@RequestParam("partID") long id, Model model) {
        Optional<Part> opt = partRepository.findById(id);
        if (!opt.isPresent()) {
            return "redirect:/mainscreen";
        }
        Part p = opt.get();
        if (p instanceof InhousePart) {
            model.addAttribute("inhousePart", p);
            return "InhousePartForm";
        } else {
            model.addAttribute("outsourcedPart", p);
            return "OutsourcedPartForm";
        }
    }

    // -------- Save handlers with min/max validation (Part G, supports H) --------

    @PostMapping("/saveInhousePart")
    public String saveInhousePart(@Valid @ModelAttribute("inhousePart") InhousePart part,
                                  BindingResult result) {

        // Enforce Max >= Min and Min ≤ Inv ≤ Max
        if (part.getMax() < part.getMin()) {
            result.rejectValue("max", "maxLessThanMin", "Max must be >= Min");
        }
        if (part.getInv() < part.getMin() || part.getInv() > part.getMax()) {
            result.rejectValue("inv", "invOutOfBounds", "Inventory must be between Min and Max");
        }

        if (result.hasErrors()) {
            return "InhousePartForm";
        }

        partRepository.save(part);
        return "redirect:/mainscreen";
    }

    @PostMapping("/saveOutsourcedPart")
    public String saveOutsourcedPart(@Valid @ModelAttribute("outsourcedPart") OutsourcedPart part,
                                     BindingResult result) {

        if (part.getMax() < part.getMin()) {
            result.rejectValue("max", "maxLessThanMin", "Max must be >= Min");
        }
        if (part.getInv() < part.getMin() || part.getInv() > part.getMax()) {
            result.rejectValue("inv", "invOutOfBounds", "Inventory must be between Min and Max");
        }

        if (result.hasErrors()) {
            return "OutsourcedPartForm";
        }

        partRepository.save(part);
        return "redirect:/mainscreen";
    }

    // -------- Delete --------

    @GetMapping("/deletepart")
    public String deletePart(@RequestParam("partID") long id) {
        partRepository.deleteById(id);
        return "redirect:/mainscreen";
    }
}
