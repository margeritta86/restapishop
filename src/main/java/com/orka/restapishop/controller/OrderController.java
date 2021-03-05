package com.orka.restapishop.controller;


import com.orka.restapishop.service.BasketService;
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
    public void placeOrder(@PathVariable Long basketId ){
        basketService.placeOrder(basketId, Optional.empty());
    }

    @PostMapping("/baskets/{basketId}/customers/{customerId}")
    public void placeOrder(@PathVariable Long basketId,@PathVariable Long customerId ){
        basketService.placeOrder(basketId, Optional.of(customerId));
    }
}
