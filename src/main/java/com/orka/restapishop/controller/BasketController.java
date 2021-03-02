package com.orka.restapishop.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.service.BasketService;
import com.orka.restapishop.service.ProductService;
import com.orka.restapishop.view.View;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private BasketService basketService;
    private ProductService productService;

    public BasketController(BasketService basketService, ProductService productService) {
        this.basketService = basketService;
        this.productService = productService;
    }

    @PostMapping("/{basketId}/products/{productId}")
    public void addProductToBasket(@PathVariable Long basketId, @PathVariable Long productId){
        basketService.saveProductToBasket(basketId,productId); // todo

    }

    //@JsonView({View.Details.class})
    @GetMapping("/{basketId}")
    public BasketDto getBasket(@PathVariable Long basketId){
        BasketDto basketDtoById = basketService.getBasketDtoById(basketId);
        System.err.println(basketDtoById);
        return basketDtoById;

    }
}
