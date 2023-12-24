package com.github.tommorowsolutions.rkpRestBackend.controllers.mvc;

import com.github.tommorowsolutions.rkpRestBackend.models.Service;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ServiceRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.page.ServicePageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/services")
public class ServiceMvcController {
    private final ServiceRepository serviceRepository;
    private final ServicePageRepository servicePageRepository;

    public ServiceMvcController(ServiceRepository serviceRepository, ServicePageRepository servicePageRepository) {
        this.serviceRepository = serviceRepository;
        this.servicePageRepository = servicePageRepository;
    }

    @GetMapping("/")
    public String Index(Model model){
        var list = servicePageRepository.findTop5ByOrderByIdDesc(PageRequest.of(0,5));
        model.addAttribute("services",list);
        return "services/Index";
    }
    @GetMapping("/loadall")
    public String LoadAll(Model model){
        var list = serviceRepository.findAll();
        model.addAttribute("services",list);
        return "services/Index";
    }
    @GetMapping("/new")
    public String Create(Model model){
        Service service = new Service();
        model.addAttribute("service",service);
        return "services/Create";

    }
    @PostMapping("/new")
    public String Create(@ModelAttribute Service service){
        serviceRepository.save(service);
        return "redirect:/services/";
    }
    @GetMapping("/details/{id}")
    public String Details(@PathVariable("id") Long id,Model model){
        var optService = serviceRepository.findById(id);
        if (optService.isEmpty()) return "redirect:/services/";
        model.addAttribute("service",optService.get() );
        return "services/Detail";
    }
    @GetMapping("/update/{id}")
    public String Update(Model model,@PathVariable("id") Long id){
        var optService = serviceRepository.findById(id);
        if (optService.isEmpty()) return "redirect:/services/";
        model.addAttribute("service",optService.get());
        return "services/Update";
    }
    @PostMapping("/update")
    public String Update(@ModelAttribute Service service){
        serviceRepository.save(service);
        return "redirect:/services/";
    }
    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable("id") Long id,Model model){
        var optService = serviceRepository.findById(id);
        if (optService.isEmpty()) return "redirect:/services/";
        model.addAttribute("service",optService.get());
        return "services/Delete";
    }
    @PostMapping("/confirmdelete/{id}")
    public String DeleteConfirmed(@PathVariable("id") Long id){
        if(serviceRepository.existsById(id)) serviceRepository.deleteById(id);
        return "redirect:/services/";
    }
}
