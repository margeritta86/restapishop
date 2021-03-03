package com.orka.restapishop.controller;


import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.service.BasketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/{basketId}/products/{productId}")
    public void addProductToBasket(@PathVariable Long basketId, @PathVariable Long productId, Integer count) {
        int actualCount = count == null ? 1 : count;
        basketService.saveProductToBasket(basketId, productId, actualCount);

    }

    @GetMapping("/{basketId}")
    public BasketDto getBasket(@PathVariable Long basketId) {
        return basketService.getBasketDtoById(basketId);

    }

    @PatchMapping()
    public void updateBasket(BasketDto basket) {//todo
        basketService.updateBasket(basket);
    }


    /*@PostMapping()
    public void setCustomerData(){

    }

    @PostMapping
    public void confirmOrder(){

    }*/


}
