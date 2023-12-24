package com.github.tommorowsolutions.rkpRestBackend.controllers.rest;

import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderDetailDto;
import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderDetailUpdateDto;
import com.github.tommorowsolutions.rkpRestBackend.models.OrderDetail;
import com.github.tommorowsolutions.rkpRestBackend.repositories.GuardedObjectRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.OrderDetailRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.OrderRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/orderDetails")
@RestController
public class OrderDetailRestController {
    private final OrderDetailRepository orderDetailRepository;
    private final ServiceRepository serviceRepository;
    private final GuardedObjectRepository guardedObjectRepository;
    private final OrderRepository orderRepository;

    public OrderDetailRestController(OrderDetailRepository orderDetailRepository, ServiceRepository serviceRepository, GuardedObjectRepository guardedObjectRepository, OrderRepository orderRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.serviceRepository = serviceRepository;
        this.guardedObjectRepository = guardedObjectRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public List<OrderDetail> getAllOrderDetails(){
        var list = orderDetailRepository.findAll();
        return (List<OrderDetail>) list;
    }
    @GetMapping("/{id}")
    public OrderDetail getOrderDetail(@PathVariable("id") Long id){
        var orderDetail = orderDetailRepository.findById(id);
        if(orderDetail.isEmpty())
            throw new EntityNotFoundException();
        return orderDetail.get();
    }
    @PostMapping("/")
    public OrderDetail postOrderDetail(@RequestBody OrderDetailDto dto){
        var optOrder = orderRepository.findById(dto.getOrderId());
        if (optOrder.isEmpty())
            throw new EntityNotFoundException();
        var optService = serviceRepository.findById(dto.getServiceId());
        if(optService.isEmpty())
            throw new EntityNotFoundException();
        var optObject = guardedObjectRepository.findById(dto.getGuardedObjectId());
        if (optObject.isEmpty())
            throw new EntityNotFoundException();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(optOrder.get());
        orderDetail.setService(optService.get());
        orderDetail.setGuardedObject(optObject.get());
        orderDetail.setQuantity(dto.getQuantity());
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }
    @PutMapping("/{id}")
    public OrderDetail putOrderDetail(@RequestBody OrderDetailUpdateDto dto, @PathVariable("id") Long id){
        if (!orderDetailRepository.existsById(id)||!id.equals(dto.getId()))
            throw new EntityNotFoundException();
        var optOD = orderDetailRepository.findById(id);
        if (optOD.isEmpty())
            throw new EntityNotFoundException();
        var optOrder = orderRepository.findById(dto.getOrderId());
        if (optOrder.isEmpty())
            throw new EntityNotFoundException();
        var optService = serviceRepository.findById(dto.getServiceId());
        if(optService.isEmpty())
            throw new EntityNotFoundException();
        var optObject = guardedObjectRepository.findById(dto.getGuardedObjectId());
        if (optObject.isEmpty())
            throw new EntityNotFoundException();
        OrderDetail orderDetail = optOD.get();
        orderDetail.setOrder(optOrder.get());
        orderDetail.setService(optService.get());
        orderDetail.setGuardedObject(optObject.get());
        orderDetail.setQuantity(dto.getQuantity());
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }
    @DeleteMapping("/{id}")
    public String deleteOrderDetail(@PathVariable("id") Long id){
        if (!orderDetailRepository.existsById(id))
            throw new EntityNotFoundException();
        orderDetailRepository.deleteById(id);
        return "Успешно удалено";
    }
}
