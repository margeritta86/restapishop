package com.orka.restapishop.excepiton;

public class CustomerNotFoundException extends RuntimeException{


    public CustomerNotFoundException(long id) {
        super("Could not find customer for this id: " + id);
    }
}
