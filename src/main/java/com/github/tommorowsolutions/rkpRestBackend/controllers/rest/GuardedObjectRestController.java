package com.github.tommorowsolutions.rkpRestBackend.controllers.rest;

import com.github.tommorowsolutions.rkpRestBackend.models.GuardedObject;
import com.github.tommorowsolutions.rkpRestBackend.repositories.GuardedObjectRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/guardedObjects")
@RestController
public class GuardedObjectRestController {
    private  final GuardedObjectRepository guardedObjectRepository;

    public GuardedObjectRestController(GuardedObjectRepository guardedObjectRepository) {
        this.guardedObjectRepository = guardedObjectRepository;
    }
    @GetMapping("/")
    public List<GuardedObject> getAllGuardedObjects(){
        var list = guardedObjectRepository.findAll();
        return (List<GuardedObject>) list;
    }
    @GetMapping("/{id}")
    public GuardedObject getGuardedObject(@PathVariable("id") Long id){
        var gObject = guardedObjectRepository.findById(id);
        if(gObject.isEmpty())
            throw new EntityNotFoundException();
        return gObject.get();
    }
    @PostMapping("/")
    public GuardedObject postGuardedObject(@RequestBody GuardedObject guardedObject){
        if(guardedObject.getId()!=0)
            throw new EntityExistsException();
        guardedObjectRepository.save(guardedObject);
        return guardedObject;
    }
    @PutMapping("/{id}")
    public GuardedObject putGuardedObject(@PathVariable Long id,@RequestBody GuardedObject guardedObject){
        if (!guardedObjectRepository.existsById(id)||!id.equals(guardedObject.getId()))
            throw new EntityNotFoundException();
        guardedObjectRepository.save(guardedObject);
        return guardedObject;
    }
    @DeleteMapping("/{id}")
    public String deleteGuardedObject(@PathVariable Long id){
        if (!guardedObjectRepository.existsById(id))
            throw new EntityNotFoundException();
        guardedObjectRepository.deleteById(id);
        return "Успешно удалено";
    }
}
