package com.orka.restapishop.service;

import com.orka.restapishop.dto.OrderDto;
import com.orka.restapishop.excepiton.CustomerNotFoundException;
import com.orka.restapishop.model.Customer;
import com.orka.restapishop.repository.CustomerRepository;
import com.orka.restapishop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;


    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }


    public List<OrderDto> getListOfOrders(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        System.err.println(customer);
        return customer.mapToDto().getOrders();
    }


}
