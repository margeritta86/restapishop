package com.orka.restapishop.excepiton;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RequestAmountExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(RequestedAmountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String requestedAmountHandler(RequestedAmountException ex){
        return ex.getMessage();
    }
}
