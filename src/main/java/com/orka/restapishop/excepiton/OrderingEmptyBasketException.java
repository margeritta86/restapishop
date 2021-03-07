package com.orka.restapishop.excepiton;

public class OrderingEmptyBasketException extends RuntimeException {
    public OrderingEmptyBasketException(Long basketId) {
        super("Your basket: "+basketId+" is empty.You cannot order an empty basket!");
    }
}
