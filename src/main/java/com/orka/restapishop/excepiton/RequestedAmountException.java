package com.orka.restapishop.excepiton;

public class RequestedAmountException extends RuntimeException {

    public RequestedAmountException(String message) {
        super(message);
    }
}