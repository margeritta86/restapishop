package com.orka.restapishop.model;

import com.orka.restapishop.excepiton.RequestedAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
class BasketTest {



    @Test
    void shouldAddProductToTheBasket() {
        //given
        Basket basket = new Basket();
        Product product  = new Product("Ham", new BigDecimal("5.07"), "imgUrl1", 10);
        //when
        basket.addProduct(product,10);
        //then
        Assertions.assertTrue(basket.getProducts().containsKey(product));

    }

    @Test
    void shouldThrowRequestedAmountException() {
        //given
        Basket basket = new Basket();
        Product product  = new Product("Ham", new BigDecimal("5.07"), "imgUrl1", 10);
        //when

        //then
        Assertions.assertThrows(RequestedAmountException.class,()-> basket.addProduct(product,0));

    }

    @Test
    void shouldUpdateAmountOfGivenProduct() {
        //given
        Product product  = new Product("Ham", new BigDecimal("5.07"), "imgUrl1", 10);
        Basket basket = new Basket();
        Map<Product,Integer> products = new HashMap<>();
        products.put(product,3);
        basket.setProducts(products);
        //when
        basket.updateProduct(product,5);
        //then
        Assertions.assertTrue(basket.getProducts().get(product)==5);


    }

    @Test
    void deleteProduct() {
        //given
        Product product  = new Product("Ham", new BigDecimal("5.07"), "imgUrl1", 10);
        Basket basket = new Basket();
        Map<Product,Integer> products = new HashMap<>();
        products.put(product,3);
        basket.setProducts(products);
        //when
        basket.deleteProduct(product);
        //then
        Assertions.assertFalse(basket.getProducts().containsKey(product));

    }

    @Test
    void calculateTotalPrice() {


    }
}