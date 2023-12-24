package com.github.tommorowsolutions.rkpRestBackend.controllers.rest;

import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderDto;
import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderUpdateDto;
import com.github.tommorowsolutions.rkpRestBackend.models.Order;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ClientRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ManagerRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/orders")
@RestController
public class OrderRestController {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;

    public OrderRestController(OrderRepository orderRepository, ClientRepository clientRepository, ManagerRepository managerRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
    }

    @GetMapping("/")
    public List<Order> getAllOrders(){
        var orders = orderRepository.findAll();
        return (List<Order>) orders;
    }
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") Long id){
        var order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new EntityNotFoundException();
        return order.get();
    }
    @PostMapping("/")
    public Order postOrder(@RequestBody OrderDto dto){
        var client = clientRepository.findById(dto.getClientId());
        if (client.isEmpty())
            throw new EntityNotFoundException();
        var manager = managerRepository.findById(dto.getManagerId());
        if (manager.isEmpty())
            throw new EntityNotFoundException();
        Order order = new Order();
        order.setClient(client.get());
        order.setManager(manager.get());
        order.setDateOfSigning(dto.getDateOfSigning());
        orderRepository.save(order);
        return order;
    }
    @PutMapping("/{id}")
    public Order putOrder(@RequestBody OrderUpdateDto dto, @PathVariable("id") Long id){
        if (!orderRepository.existsById(id)||!id.equals(dto.getId()))
            throw new EntityNotFoundException();
        var optOrder = orderRepository.findById(id);
        if (optOrder.isEmpty())
            throw new EntityNotFoundException();
        var optClient = clientRepository.findById(dto.getClientId());
        if (optClient.isEmpty())
            throw new EntityNotFoundException();
        var optManager = managerRepository.findById(dto.getManagerId());
        if (optManager.isEmpty())
            throw new EntityNotFoundException();
        Order order = optOrder.get();
        order.setPrice(dto.getPrice());
        order.setDateOfComplete(dto.getDateOfComplete());
        order.setDateOfSigning(dto.getDateOfSigning());
        order.setManager(optManager.get());
        order.setClient(optClient.get());
        orderRepository.save(order);
        return order;
    }
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") Long id){
        if (!orderRepository.existsById(id))
            throw new EntityNotFoundException();
        orderRepository.deleteById(id);
        return "Успешно удалено";
    }
}
