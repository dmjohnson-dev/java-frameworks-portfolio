package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainScreenController {

    @GetMapping({"/", "/mainscreen"})
    public String mainscreen() {
        // Renders src/main/resources/templates/mainscreen.html
        return "mainscreen";
    }
    @GetMapping("/about")
    public String about() {
        return "about"; // renders src/main/resources/templates/about.html
    }

}
