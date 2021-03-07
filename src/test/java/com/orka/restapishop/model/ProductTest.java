package com.orka.restapishop.model;

import com.orka.restapishop.excepiton.RequestedAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
class ProductTest {

    @Test
    void shouldReturnTrueValueForTheProduct() {
        //given
        Product product = new Product("Ham",new BigDecimal("10.00"),"imgUrl1",10);

        //when
        //then
        Assertions.assertTrue(product.isAvailable());

    }

    @Test
    void shouldReturnFalseValueForTheGivenProduct() {
        //given
        Product product = new Product("Ham",new BigDecimal("10.00"),"imgUrl1",0);

        //when
        //then
        Assertions.assertFalse(product.isAvailable());

    }



    @Test
    void shouldDecreaseAmountOfTheGivenProduct() {
        //given
        Product product = new Product("Ham",new BigDecimal("10.00"),"imgUrl1",10);
        //when
        product.decreaseAmount(5);
        //then
        Assertions.assertTrue(product.getAmount()==5);

    }

    @Test
    void shouldThrowRequestedAmountExceptionForTooLargeAmountOfProductToDecrease(){
        //given
        Product product = new Product("Ham",new BigDecimal("10.00"),"imgUrl1",3);
        //when
        //then
        Assertions.assertThrows(RequestedAmountException.class,()->product.decreaseAmount(5));

    }
}