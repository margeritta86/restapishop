package com.orka.restapishop.controller;

import com.orka.restapishop.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    BasketService basketService;

    public OrderController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/baskets/{basketId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long basketId ){
        basketService.placeOrder(basketId, Optional.empty());
        return ResponseEntity.ok("You added order succesfully!");
    }

    @PostMapping("/baskets/{basketId}/customers/{customerId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long basketId, @PathVariable Long customerId ){
        basketService.placeOrder(basketId, Optional.of(customerId));
        return ResponseEntity.ok("You added order succesfully!");
    }
}
