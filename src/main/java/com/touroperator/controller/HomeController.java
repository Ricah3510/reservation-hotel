package com.touroperator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello World from Spring MVC!");
        model.addAttribute("titre", "Système de Gestion des Réservations");
        return "home";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Page de test");
        return "home";
    }
}
