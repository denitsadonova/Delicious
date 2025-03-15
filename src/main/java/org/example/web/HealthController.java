package org.example.web;

import org.example.models.enums.Minerals;
import org.example.models.enums.Vitamins;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {
        @GetMapping("/health")
        public String healthPage(Model model) {
            model.addAttribute("vitamins", Vitamins.values());
            model.addAttribute("minerals", Minerals.values());
            return "health";
        }
    }

