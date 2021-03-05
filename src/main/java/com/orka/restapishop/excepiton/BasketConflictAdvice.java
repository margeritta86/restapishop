package com.orka.restapishop.excepiton;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BasketConflictAdvice {

    @ResponseBody
    @ExceptionHandler(BasketConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String basketConflictHandler(BasketConflictException ex){
        return ex.getMessage();
    }
}
