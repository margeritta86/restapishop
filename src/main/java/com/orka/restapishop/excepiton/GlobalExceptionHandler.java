package com.orka.restapishop.excepiton;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Validation Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFoundErrorHandling(ProductNotFoundException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Product Not Found Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(RequestedAmountException.class)
    public ResponseEntity<?> requestedAmountErrorHandling(RequestedAmountException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Requested Amount Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(RequestedValueException.class)
    public ResponseEntity<?> requestedValueErrorHandling(RequestedValueException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Requested Value Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler(DeliveryDataRequiredException.class)
    public ResponseEntity<?> deliveryDataRequiredErrorHandling(DeliveryDataRequiredException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Delivery Data Required Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundErrorHandling(CustomerNotFoundException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Customer Not Found Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(BasketNotFoundException.class)
    public ResponseEntity<?> basketNotFoundErrorHandling(BasketNotFoundException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Basket Not Found Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(BasketConflictException.class)
    public ResponseEntity<?> basketConflictErrorHandling(BasketConflictException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Basket Conflict Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(OrderingEmptyBasketException.class)
    public ResponseEntity<?> orderingEmptyBasketErrorHandling(OrderingEmptyBasketException ex){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Ordering Empty Basket Error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }




}
