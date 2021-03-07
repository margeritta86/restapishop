package com.orka.restapishop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
class CustomerTest {

    @Test
    void addingOrderToListShouldAddOrderToCustomersList() {
        //given
        Order order = new Order();
        Customer customer = new Customer();
        //when
        customer.addOrderToList(order);
        //then
        Assertions.assertTrue(customer.getOrders().contains(order));


    }
}