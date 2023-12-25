package com.github.tommorowsolutions.rkpRestBackend.controllers.mvc;


import com.github.tommorowsolutions.rkpRestBackend.models.Client;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ClientRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.page.ClientPageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RequestMapping(value={"/clients","","/"})
@Controller
public class ClientMvcController {
    private final ClientPageRepository clientPageRepository;
    private final ClientRepository clientRepository;
    private final Logger logger = Logger.getLogger(ClientMvcController.class.getName());

    public ClientMvcController(ClientPageRepository clientPageRepository, ClientRepository clientRepository) {
        this.clientPageRepository = clientPageRepository;
        this.clientRepository = clientRepository;
    }

    //Получаем первых пять клиентов
    @GetMapping("/")
    public String Index(Model model){
        var clients = clientPageRepository.findTop5ByOrderByIdDesc(PageRequest.of(0, 5)).getContent();
        model.addAttribute("clients", clients);
        return "clients/Index";
    }
    @GetMapping("/loadall")
    public String IndexLoadAll(Model model){
        var clients = clientRepository.findAll();
        model.addAttribute("clients",clients);
        return "clients/Index";
    }
    @GetMapping("/details/{id}")
    public String Details(Model model,@PathVariable("id") Long id){
        var optClient = clientRepository.findById(id);
        if(optClient.isEmpty()) return "redirect:/clients/";
        model.addAttribute("client",optClient.get());
        return "clients/Detail";
    }
    @GetMapping("/new")
    public String Create(Model model){
        model.addAttribute("client",new Client());
        model.addAttribute("typesOfPerson", new String[]{"Физическое", "Юридическое"});
        return "clients/Create";
    }
    @PostMapping("/new")
    public String Create(@ModelAttribute Client client,Model model){
        clientRepository.save(client);
        return "redirect:/clients/";
    }
    @GetMapping("/update/{id}")
    public String Update(Model model,@PathVariable("id") Long id){
        var optClient = clientRepository.findById(id);
        if (optClient.isEmpty()) return "redirect:/clients/";
        model.addAttribute("client",optClient.get());
        model.addAttribute("typesOfPerson", new String[]{"Физическое", "Юридическое"});
        return "clients/Update";
    }
    @PostMapping("/update")
    public String Update(@ModelAttribute Client client,Model model){
        clientRepository.save(client);
        return "redirect:/clients/";
    }
    @GetMapping("/delete/{id}")
    public String Delete(Model model,@PathVariable("id") Long id){
        var optClient = clientRepository.findById(id);
        if (optClient.isEmpty()) return "redirect:/clients/";
        model.addAttribute("client",optClient.get());
        model.addAttribute("typesOfPerson", new String[]{"Физическое", "Юридическое"});
        return "clients/Delete";
    }
    @PostMapping("/confirmdelete/{id}")
    public String DeleteConfirmed(@PathVariable("id") Long id, Model model){
        if (clientRepository.existsById(id)) clientRepository.deleteById(id);
        return "redirect:/clients/";
    }
}
