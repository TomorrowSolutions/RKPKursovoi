package com.github.tommorowsolutions.rkpRestBackend.controllers.mvc;

import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderDetailDto;
import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderDetailUpdateDto;
import com.github.tommorowsolutions.rkpRestBackend.models.OrderDetail;
import com.github.tommorowsolutions.rkpRestBackend.repositories.GuardedObjectRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.OrderDetailRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.OrderRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.ServiceRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.page.OrderDetailPageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderdetails")
public class OrderDetailMvcController {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailPageRepository orderDetailPageRepository;
    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;
    private final GuardedObjectRepository guardedObjectRepository;

    public OrderDetailMvcController(OrderDetailRepository orderDetailRepository, OrderDetailPageRepository orderDetailPageRepository, OrderRepository orderRepository, ServiceRepository serviceRepository, GuardedObjectRepository guardedObjectRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderDetailPageRepository = orderDetailPageRepository;
        this.orderRepository = orderRepository;
        this.serviceRepository = serviceRepository;
        this.guardedObjectRepository = guardedObjectRepository;
    }

    @GetMapping("/")
    public String Index(Model model){
        var list = orderDetailPageRepository.findTop5ByOrderByIdDesc(PageRequest.of(0,5));
        model.addAttribute("orderDetails",list);
        return "orderdetails/Index";
    }
    @GetMapping("/loadall")
    public String LoadAll(Model model){
        var list = orderDetailRepository.findAll();
        model.addAttribute("orderDetails",list);
        return "orderdetails/Index";
    }
    @GetMapping("/new")
    public String Create(Model model){
        OrderDetailDto dto  = new OrderDetailDto();
        model.addAttribute("ordDetDto",dto);
        model.addAttribute("orders",orderRepository.findAll());
        model.addAttribute("services",serviceRepository.findAll());
        model.addAttribute("guardedObjects",guardedObjectRepository.findAll());
        return "orderdetails/Create";
    }
    @PostMapping("/new")
    public String Create(@ModelAttribute OrderDetailDto dto){
        OrderDetail orderDetail = new OrderDetail();
        var optOrder = orderRepository.findById(dto.getOrderId());
        if (optOrder.isEmpty()) throw new EntityNotFoundException();
        orderDetail.setOrder(optOrder.get());
        var optService = serviceRepository.findById(dto.getServiceId());
        if (optService.isEmpty()) throw new EntityNotFoundException();
        orderDetail.setService(optService.get());
        var optObj = guardedObjectRepository.findById(dto.getGuardedObjectId());
        if (optObj.isEmpty()) throw new EntityNotFoundException();
        orderDetail.setGuardedObject(optObj.get());
        orderDetail.setQuantity(dto.getQuantity());
        orderDetailRepository.save(orderDetail);
        return "redirect:/orderdetails/";
    }
    @GetMapping("/update/{id}")
    public String Update(@PathVariable("id") Long id,Model model){
        var optOD = orderDetailRepository.findById(id);
        if (optOD.isEmpty()) return "redirect:/orderdetails/";
        OrderDetailUpdateDto dto = new OrderDetailUpdateDto();
        OrderDetail orderDetail = optOD.get();
        dto.setId(orderDetail.getId());
        dto.setOrderId(orderDetail.getOrder().getId());
        dto.setServiceId(orderDetail.getService().getId());
        dto.setGuardedObjectId(orderDetail.getGuardedObject().getId());
        dto.setQuantity(orderDetail.getQuantity());
        model.addAttribute("ordDetDto",dto);
        model.addAttribute("orders",orderRepository.findAll());
        model.addAttribute("services",serviceRepository.findAll());
        model.addAttribute("guardedObjects",guardedObjectRepository.findAll());
        return "orderdetails/Update";
    }
    @PostMapping("/update")
    public String Update(@ModelAttribute OrderDetailUpdateDto dto){
        var optOD = orderDetailRepository.findById(dto.getId());
        if (optOD.isEmpty()) return "redirect:/orderdetails/";
        OrderDetail orderDetail = optOD.get();
        var optOrder = orderRepository.findById(dto.getOrderId());
        if (optOrder.isEmpty()) throw new EntityNotFoundException();
        orderDetail.setOrder(optOrder.get());
        var optService = serviceRepository.findById(dto.getServiceId());
        if (optService.isEmpty()) throw new EntityNotFoundException();
        orderDetail.setService(optService.get());
        var optObj = guardedObjectRepository.findById(dto.getGuardedObjectId());
        if (optObj.isEmpty()) throw new EntityNotFoundException();
        orderDetail.setGuardedObject(optObj.get());
        orderDetail.setQuantity(dto.getQuantity());
        orderDetailRepository.save(orderDetail);
        return "redirect:/orderdetails/";
    }
    @GetMapping("/details/{id}")
    public String Details(@PathVariable("id") Long id,Model model){
        var optOD = orderDetailRepository.findById(id);
        if (optOD.isEmpty()) return "redirect:/orderdetails/";
        model.addAttribute("orderDetail",optOD.get());
        return "orderdetails/Detail";
    }
    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable("id") Long id, Model model){
        var optOD = orderDetailRepository.findById(id);
        if (optOD.isEmpty()) return "redirect:/orderdetails/";
        model.addAttribute("orderDetail",optOD.get());
        return "orderdetails/Delete";
    }
    @PostMapping("/confirmdelete/{id}")
    public String ConfirmDelete(@PathVariable("id") Long id){
        if (orderDetailRepository.existsById(id)) orderDetailRepository.deleteById(id);
        return "redirect:/orderdetails/";
    }
}
