package com.github.tommorowsolutions.rkpRestBackend.controllers.mvc;

import com.github.tommorowsolutions.rkpRestBackend.models.Manager;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ManagerRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.page.ManagerPageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller()
@RequestMapping("/managers")
public class ManagerMvcController {
    private final ManagerRepository managerRepository;
    private final ManagerPageRepository managerPageRepository;

    public ManagerMvcController(ManagerRepository managerRepository, ManagerPageRepository managerPageRepository) {
        this.managerRepository = managerRepository;
        this.managerPageRepository = managerPageRepository;
    }
    @GetMapping("/")
    private String Index(Model model){
        var list = managerPageRepository.findTop5ByOrderByIdDesc(PageRequest.of(0,5)).getContent();
        model.addAttribute("managers",list);
        return "managers/Index";
    }
    @GetMapping("/loadall")
    private String IndexLoadAll(Model model){
        model.addAttribute("managers",managerRepository.findAll());
        return "managers/Index";
    }
    @GetMapping("/details/{id}")
    private String Detail(@PathVariable("id") Long id,Model model){
        var optMan=managerRepository.findById(id);
        if (optMan.isEmpty()) return "redirect:/managers/";
        model.addAttribute("manager",optMan.get());
        return "managers/Detail";
    }
    @GetMapping("/new")
    private String Create(Model model){
        Manager manager = new Manager();
        model.addAttribute("manager",manager);
        model.addAttribute("edu",new String[]{"Начальное","Среднеее","Среднее профессиональное","Высшее"});
        return "managers/Create";
    }
    @PostMapping("/new")
    private String Create(@ModelAttribute Manager manager,Model model){
        managerRepository.save(manager);
        return "redirect:/managers/";
    }
    @GetMapping("/update/{id}")
    private String Update(@PathVariable("id") Long id,Model model){
        var optMan = managerRepository.findById(id);
        if (optMan.isEmpty()) return "redirect:/managers/";
        model.addAttribute("manager",optMan.get());
        model.addAttribute("edu",new String[]{"Начальное","Среднеее","Среднее профессиональное","Высшее"});
        return "managers/Update";
    }
    @PostMapping("/update")
    private String Update(@ModelAttribute Manager manager,Model model){
        managerRepository.save(manager);
        return "redirect:/managers/";
    }
    @GetMapping("/delete/{id}")
    private String Delete(@PathVariable("id") Long id,Model model){
        var optMan = managerRepository.findById(id);
        if (optMan.isEmpty()) return "redirect:/managers/";
        model.addAttribute("manager",optMan.get());
        return "managers/Delete";
    }
    @PostMapping("/confirmdelete/{id}")
    private String DeleteConfirmed(@PathVariable("id") Long id){
        if (managerRepository.existsById(id)) managerRepository.deleteById(id);
        return "redirect:/managers/";
    }

}
