package com.orka.restapishop.service;

import com.orka.restapishop.dto.OrderDto;
import com.orka.restapishop.excepiton.CustomerNotFoundException;
import com.orka.restapishop.model.Customer;
import com.orka.restapishop.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<OrderDto> getListOfOrders(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return customer.mapToDto().getOrders();
    }


}
