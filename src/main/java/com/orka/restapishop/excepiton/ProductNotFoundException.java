package com.orka.restapishop.excepiton;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(long id) {
        super("Could not find product for this id " + id);
    }
}
