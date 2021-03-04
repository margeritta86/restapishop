package com.orka.restapishop.excepiton;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RequestedValueAdvice {

    @ResponseBody
    @ExceptionHandler(RequestedValueException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String productNotFoundHandler(RequestedValueException ex){
        return ex.getMessage();
    }
}
