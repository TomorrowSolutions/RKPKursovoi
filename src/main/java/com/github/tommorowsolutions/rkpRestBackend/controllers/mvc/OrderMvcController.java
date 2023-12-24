package com.github.tommorowsolutions.rkpRestBackend.controllers.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderDetailDto;
import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderEmbDetDto;
import com.github.tommorowsolutions.rkpRestBackend.dtos.OrderUpdateDto;
import com.github.tommorowsolutions.rkpRestBackend.models.Order;
import com.github.tommorowsolutions.rkpRestBackend.models.OrderDetail;
import com.github.tommorowsolutions.rkpRestBackend.repositories.*;
import com.github.tommorowsolutions.rkpRestBackend.repositories.page.OrderPageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.time.LocalDate;

@Controller
@RequestMapping("/orders")
public class OrderMvcController {
    private final Logger logger= Logger.getLogger(OrderMvcController.class.getName());
    private final OrderPageRepository orderPageRepository;
    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;

    private final ServiceRepository serviceRepository;
    private final GuardedObjectRepository guardedObjectRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderMvcController(OrderPageRepository orderPageRepository, OrderRepository orderRepository, ClientRepository clientRepository, ManagerRepository managerRepository, ServiceRepository serviceRepository, GuardedObjectRepository guardedObjectRepository, OrderDetailRepository orderDetailRepository) {
        this.orderPageRepository = orderPageRepository;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.serviceRepository = serviceRepository;
        this.guardedObjectRepository = guardedObjectRepository;
        this.orderDetailRepository = orderDetailRepository;
    }
    @GetMapping("/")
    public String Index(Model model){
        var orders = orderPageRepository.findTop5ByOrderByIdDesc(PageRequest.of(0,5)).getContent();
        model.addAttribute("orders",orders);
        return "orders/Index";
    }
    @GetMapping("/loadall")
    public String IndexLoadAll(Model model){
        var orders = orderRepository.findAll();
        model.addAttribute("orders",orders);
        return "orders/Index";
    }
    @GetMapping("/details/{id}")
    public String Details(Model model,@PathVariable("id") Long id){
        var optOrder = orderRepository.findById(id);
        if(optOrder.isEmpty()) return "redirect:/orders/";
        Order order = optOrder.get();
        model.addAttribute("order",optOrder.get());

        return "orders/Detail";
    }
    @GetMapping("/new")
    public String Create(Model model){
        model.addAttribute("clients",clientRepository.findAll());
        model.addAttribute("orderDto",new OrderEmbDetDto());
        model.addAttribute("managers",managerRepository.findAll());
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("gObjects",guardedObjectRepository.findAll());
        return "orders/Create";
    }
    @PostMapping("/new")
    public String Create(@ModelAttribute OrderEmbDetDto dto,Model model){
        Order order = new Order();
        var optMan = managerRepository.findById(dto.getManagerId());
        if (optMan.isEmpty())
            throw new EntityNotFoundException();
        order.setManager(optMan.get());
        var optClient = clientRepository.findById(dto.getClientId());
        if (optClient.isEmpty())
            throw new EntityNotFoundException();
        order.setClient(optClient.get());
        order.setDateOfSigning(dto.getDateOfSigning());
        orderRepository.save(order);
        //Получаем массив в виде строки
        String strArr = (String)dto.getListOfDetails();
        if (strArr!= null&&!strArr.trim().isEmpty()){
            String[] splitted = strArr.split(",");
            long[] detailsArr = Stream.of(splitted).mapToLong(Long::parseLong).toArray();
            for (int i=0;i<detailsArr.length;i+=3){
                OrderDetailDto temp = new OrderDetailDto();
                temp.setOrderId(order.getId());
                temp.setGuardedObjectId(detailsArr[i]);
                temp.setServiceId(detailsArr[i+1]);
                temp.setQuantity((int)detailsArr[i+2]);
                InsertDetail(temp);
            }
        }
        var list =orderDetailRepository.findByOrderId(order.getId());
        double sumPrice = 0; int sumDays=0;
        for (var od:list) {
            sumPrice+=od.getService().getPrice();
            sumDays+=od.getService().getPeriodOfExecution();
            sumDays*=od.getQuantity();
            sumPrice*=od.getQuantity();
        }
        order.setPrice(sumPrice);
        var finDate = LocalDate.of(order.getDateOfSigning().getYear(),order.getDateOfSigning().getMonthValue(),order.getDateOfSigning().getDayOfMonth());
        order.setDateOfComplete(finDate.plusDays(sumDays));
        order.setOrderDetails(list);
        orderRepository.save(order);
        return "redirect:/orders/details/"+order.getId();
    }
    private void InsertDetail(OrderDetailDto dto){
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
    }
    @GetMapping("/update/{id}")
    public String Update(Model model,@PathVariable("id") Long id){
        var optOrder = orderRepository.findById(id);
        if (optOrder.isEmpty()) return "redirect:/orders/";
        var order = optOrder.get();
        var dto = new OrderUpdateDto();
        dto.setId(order.getId());
        dto.setClientId(order.getClient().getId());
        dto.setManagerId(order.getManager().getId());
        dto.setDateOfSigning(order.getDateOfSigning());
        dto.setDateOfComplete(order.getDateOfComplete());
        dto.setPrice(order.getPrice());
        model.addAttribute("clients",clientRepository.findAll());
        model.addAttribute("orderDto",dto);
        model.addAttribute("managers",managerRepository.findAll());
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("gObjects",guardedObjectRepository.findAll());
        return "orders/Update";
    }

    @GetMapping("/recalculate/{id}")
    public String Recalculate(@PathVariable("id") Long id){
        var optOrder = orderRepository.findById(id);
        if (optOrder.isEmpty()) return "redirect:/orders/";
        Order order = optOrder.get();
        var list =orderDetailRepository.findByOrderId(order.getId());
        double sumPrice = 0; int sumDays=0;
        for (var od:list) {
            sumPrice+=od.getService().getPrice();
            sumDays+=od.getService().getPeriodOfExecution();
            sumDays*=od.getQuantity();
            sumPrice*=od.getQuantity();
        }
        order.setPrice(sumPrice);
        var finDate = LocalDate.of(order.getDateOfSigning().getYear(),order.getDateOfSigning().getMonthValue(),order.getDateOfSigning().getDayOfMonth());
        order.setDateOfComplete(finDate.plusDays(sumDays));
        order.setOrderDetails(list);
        orderRepository.save(order);
        return "redirect:/orders/details/"+order.getId();
    }
    @PostMapping("/update")
    public String Update(@ModelAttribute OrderUpdateDto dto,Model model){
        if (!orderRepository.existsById(dto.getId()))
            throw new EntityNotFoundException();
        var optOrder = orderRepository.findById(dto.getId());
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
        return "redirect:/orders/";
    }
    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable("id")Long id,Model model){
        var optOrder = orderRepository.findById(id);
        if (optOrder.isEmpty()) return  "redirect:/orders/";
        model.addAttribute("order",optOrder.get());
        return "orders/Delete";
    }
    @PostMapping("/confirmdelete/{id}")
    public String ConfirmDelete(@PathVariable("id")Long id,Model model){
        if (orderRepository.existsById(id)) orderRepository.deleteById(id);
        return "redirect:/orders/";
    }

}
