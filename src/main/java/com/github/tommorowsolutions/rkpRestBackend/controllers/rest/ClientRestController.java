package com.github.tommorowsolutions.rkpRestBackend.controllers.rest;

import com.github.tommorowsolutions.rkpRestBackend.models.Client;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ClientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/clients")
@RestController
public class ClientRestController {
    private final ClientRepository clientRepository;

    public ClientRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @GetMapping("/")
    public List<Client> getAllClients(){
        var list = clientRepository.findAll();
        return (List<Client>) list;
    }
    @GetMapping("/{id}")
    public Client getClient(@PathVariable("id") Long id){
        var client = clientRepository.findById(id);
        if (client.isEmpty())
            throw new EntityNotFoundException();
        return client.get();
    }
    @PostMapping("/")
    public Client postClient(@RequestBody Client client){
        if (client.getId()!=0)
            throw new EntityExistsException();
        clientRepository.save(client);
        return client;
    }
    @PutMapping("/{id}")
    public Client putClient(@RequestBody Client client,@PathVariable("id") Long id){
        if (!clientRepository.existsById(id)||!id.equals(client.getId()))
            throw new EntityNotFoundException();
        clientRepository.save(client);
        return client;
    }
    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable("id") Long id){
        if(!clientRepository.existsById(id))
            throw new EntityNotFoundException();
        clientRepository.deleteById(id);
        return "Успешно удалено";
    }
}
