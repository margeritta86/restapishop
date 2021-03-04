package com.orka.restapishop.excepiton;

public class DeliveryDataRequiredException extends RuntimeException{

    public DeliveryDataRequiredException() {
        super("Customer for this Request is required");
    }
}
