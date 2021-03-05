package com.orka.restapishop.excepiton;

public class BasketConflictException extends RuntimeException {

    public BasketConflictException(Long id, Long basketId) {
        super("Basket's id conflicts for the ids: " + id + " and: " + basketId);
    }
}
