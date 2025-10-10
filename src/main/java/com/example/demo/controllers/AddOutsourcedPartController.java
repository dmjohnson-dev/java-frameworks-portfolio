package com.example.demo.controllers;

import com.example.demo.domain.OutsourcedPart;
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
public class AddOutsourcedPartController {

    private final PartRepository partRepository;

    public AddOutsourcedPartController(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutsourcedPart(Model model) {
        model.addAttribute("outsourcedpart", new OutsourcedPart());
        return "OutsourcedPartForm";
    }

    @PostMapping("/saveOutsourcedPart")
    public String saveOutsourcedPart(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part,
                                     BindingResult br,
                                     RedirectAttributes ra) {

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
            return "OutsourcedPartForm";
        }
        partRepository.save(part);
        ra.addFlashAttribute("message", "Outsourced part saved.");
        return "redirect:/mainscreen";
    }
}
