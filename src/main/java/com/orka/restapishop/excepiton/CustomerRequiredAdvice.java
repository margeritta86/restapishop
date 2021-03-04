package com.orka.restapishop.excepiton;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerRequiredAdvice {

    @ResponseBody
    @ExceptionHandler(CustomerRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String productNotFoundHandler(CustomerRequiredException ex){
        return ex.getMessage();
    }
}
