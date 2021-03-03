package com.orka.restapishop.controller;

import com.orka.restapishop.dto.OrderDto;
import com.orka.restapishop.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public List<OrderDto> getListOfOrders(@PathVariable Long id) {
        return customerService.getListOfOrders(id);
    }
}
