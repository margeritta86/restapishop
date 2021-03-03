package com.orka.restapishop.excepiton;

public class BasketNotFoundException extends RuntimeException {

    public BasketNotFoundException(long id) {
        super("Could not find basket for this id  " + id);
    }
}
