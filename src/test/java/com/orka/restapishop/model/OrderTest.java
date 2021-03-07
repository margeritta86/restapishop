package com.orka.restapishop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void settingBasketShouldReturnAddedBasketToOrder() {
        //given
        Order order = new Order();
        Basket basket = new Basket();
        //when
        order.setBasket(basket);
        //then
        Assertions.assertEquals(basket,order.getBasket());
    }
}