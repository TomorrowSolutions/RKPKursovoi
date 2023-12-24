package com.github.tommorowsolutions.rkpRestBackend.controllers.rest;

import com.github.tommorowsolutions.rkpRestBackend.models.Manager;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ManagerRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/managers")
@RestController
public class ManagerRestController {
    private final ManagerRepository managerRepository;

    public ManagerRestController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }
    @GetMapping("/")
    public List<Manager> getAllManagers(){
        var list = managerRepository.findAll();
        return (List<Manager>) list;
    }
    @GetMapping("/{id}")
    public Manager getManagerById(@PathVariable("id") Long id){
        var manager =managerRepository.findById(id);
        if (manager.isEmpty())
            throw new EntityNotFoundException();
        return manager.get();
    }
    @PostMapping("/")
    public Manager postManager(@RequestBody Manager manager){
        if(manager.getId()!=0)
            throw new EntityExistsException();
        managerRepository.save(manager);
        return manager;
    }
    @PutMapping("/{id}")
    public Manager putManager(@RequestBody Manager manager,@PathVariable("id") Long id){
        if (!managerRepository.existsById(id)||!id.equals(manager.getId()))
            throw new EntityNotFoundException();
        managerRepository.save(manager);
        return manager;
    }
    @DeleteMapping("/{id}")
    public String  deleteManager(@PathVariable("id") Long id){
        if (!managerRepository.existsById(id))
            throw new EntityNotFoundException();
        //Не удаляет
        //У главной сущности(у которой нет join column) поставить orphanRemoval = true
        managerRepository.deleteById(id);
        return "Успешно удалено";
    }
}
