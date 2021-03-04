package com.orka.restapishop.excepiton;

public class CustomerRequiredException extends RuntimeException{

    public CustomerRequiredException() {
        super("Customer for this Request is required");
    }
}
