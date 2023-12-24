package com.github.tommorowsolutions.rkpRestBackend.controllers.rest;

import com.github.tommorowsolutions.rkpRestBackend.models.Service;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ServiceRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceRestController {
    private final ServiceRepository serviceRepository;

    public ServiceRestController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }
    @GetMapping("/")
    public List<Service> getAllServices(){
        var list = serviceRepository.findAll();
        return (List<Service>) list;
    }
    @GetMapping("/{id}")
    public Service getService(@PathVariable("id") Long id){
        var service = serviceRepository.findById(id);
        if (service.isEmpty())
            throw new EntityNotFoundException();
        return service.get();
    }
    @PostMapping("/")
    public Service postService(@RequestBody Service service){
        if(service.getId()!=0)
            throw new EntityExistsException();
        serviceRepository.save(service);
        return service;
    }
    @PutMapping("/{id}")
    public Service putService(@PathVariable("id") Long id,@RequestBody Service service){
        if (!serviceRepository.existsById(id)||!id.equals(service.getId()))
            throw new EntityNotFoundException();
        serviceRepository.save(service);
        return service;
    }
    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable("id") Long id){
        if(!serviceRepository.existsById(id))
            throw new EntityNotFoundException();
        serviceRepository.deleteById(id);
        return "Успешно удалено";
    }
}
