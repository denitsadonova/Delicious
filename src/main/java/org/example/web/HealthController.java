package org.example.web;

import jakarta.validation.Valid;
import org.example.models.binding.MineralAddBindingModel;
import org.example.models.binding.VitaminAddBindingModel;
import org.example.models.entity.MineralEntity;
import org.example.models.entity.VitaminEntity;
import org.example.repository.MineralRepository;
import org.example.repository.VitaminRepository;
import org.example.service.MineralService;
import org.example.service.VitaminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HealthController {
    private final VitaminService vitaminService;
    private final MineralService mineralService;
    private final VitaminRepository vitaminRepository;
    private final MineralRepository mineralRepository;

    public HealthController(VitaminService vitaminService, MineralService mineralService, VitaminRepository vitaminRepository, MineralRepository mineralRepository) {
        this.vitaminService = vitaminService;
        this.mineralService = mineralService;
        this.vitaminRepository = vitaminRepository;
        this.mineralRepository = mineralRepository;
    }

    @GetMapping("/health")
    public String showHealthPage(Model model) {
        model.addAttribute("vitamins", vitaminService.getAllVitamins());
        model.addAttribute("minerals", mineralService.getAllMinerals());
        return "health";
    }
@GetMapping("add/vitamin")
public String showAddVitaminPage() {
        return "add-vitamin";
}

    @PostMapping("/add/vitamin")
    public String addVitaminConfirm(@Valid VitaminAddBindingModel vitaminAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("vitaminAddBindingModel", vitaminAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vitaminAddBindingModel", bindingResult);
            return "redirect:/add/vitamin";
        }
        List<VitaminEntity> allVitamins = vitaminRepository.findAll();
        model.addAllAttributes(allVitamins);
        vitaminService.addVitamin(vitaminAddBindingModel);

        return "redirect:/health";
    }
    @GetMapping("add/mineral")
    public String showAddMineralPage() {
        return "add-mineral";
    }
    @PostMapping("/add/mineral")
    public String addMineralConfirm(@Valid MineralAddBindingModel mineralAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mineralAddBindingModel", mineralAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mineralAddBindingModel", bindingResult);
            return "redirect:addMineral";
        }
        List<MineralEntity> allMinerals = mineralRepository.findAll();
        model.addAllAttributes(allMinerals);
        mineralService.addMineral(mineralAddBindingModel);
        return "redirect:/health";
    }
    @ModelAttribute
    public VitaminAddBindingModel vitaminAddBindingModel(){
        return  new VitaminAddBindingModel();
    }
    @ModelAttribute
    public MineralAddBindingModel mineralAddBindingModel(){
        return  new MineralAddBindingModel();
    }


}

