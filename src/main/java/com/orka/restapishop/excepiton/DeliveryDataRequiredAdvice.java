package com.orka.restapishop.excepiton;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DeliveryDataRequiredAdvice {

    @ResponseBody
    @ExceptionHandler(DeliveryDataRequiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String basketConflictHandler(DeliveryDataRequiredException ex){
        return ex.getMessage();
    }
}
