package com.orka.restapishop.controller;

import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.excepiton.BasketConflictException;
import com.orka.restapishop.service.BasketService;
import jakarta.validation.Valid;
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

    @PatchMapping("/{basketId}/products")
    public void updateBasketProducts(@RequestBody BasketDto basket, @PathVariable Long basketId) {

        if (basket.getId() != basketId && basket.getId() != 0) {
            throw new BasketConflictException(basketId, basket.getId());
        }
        basketService.updateBasketProducts(basket, basketId);
    }

    @PatchMapping("/{basketId}/discount")
    public void updateBasketDiscountCode(@RequestBody BasketDto basket, @PathVariable Long basketId) {

        if (basket.getId() != basketId && basket.getId() != 0) {
            throw new BasketConflictException(basketId, basket.getId());
        }
        basketService.updateBasketDiscountCode(basket, basketId);
    }


    @DeleteMapping("{basketId}/products/{productId}")
    public void deleteProductFromBasket(@PathVariable Long basketId, @PathVariable Long productId) {
        basketService.deleteProductFromBasket(basketId, productId);

    }

    @PostMapping("{basketId}/customers/")
    public DeliveryDataDto setDeliveryData(@PathVariable Long basketId, @Valid @RequestBody DeliveryDataDto deliveryData) {


        return basketService.setDeliveryData(basketId, deliveryData);
    }

    @GetMapping("/{basketId}/calculate")
    public BasketDto recalculateBasketTotalPrice(@PathVariable Long basketId){
       return basketService.recalculateBasketTotalPRice(basketId);
    }

}
