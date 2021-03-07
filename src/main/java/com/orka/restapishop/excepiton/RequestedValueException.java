package com.orka.restapishop.excepiton;

public class RequestedValueException extends RuntimeException{

    public RequestedValueException(String value) {
        super("Requested value: "+ value+" is incorrect. Try to use 'min' or 'max' values" );
    }
}
