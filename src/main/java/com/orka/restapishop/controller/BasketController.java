package com.orka.restapishop.controller;

import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.excepiton.BasketConflictException;
import com.orka.restapishop.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/{basketId}/products/{productId}")
    public ResponseEntity<String> addProductToBasket(@PathVariable Long basketId, @PathVariable Long productId, Integer count) {
        int actualCount = count == null ? 1 : count;
        basketService.saveProductToBasket(basketId, productId, actualCount);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/{basketId}")
    public BasketDto getBasket(@PathVariable Long basketId) {
        return basketService.getBasketDtoById(basketId);

    }

    @PatchMapping("/{basketId}/products")
    public ResponseEntity<String> updateBasketProducts(@RequestBody BasketDto basket, @PathVariable Long basketId) {

        if (basket.getId() != basketId && basket.getId() != 0) {
            throw new BasketConflictException(basketId, basket.getId());
        }
        basketService.updateBasketProducts(basket, basketId);
        return ResponseEntity.ok(("You updated products in basket successfully!"));
    }

    @PatchMapping("/{basketId}/discount")
    public ResponseEntity<String> updateBasketDiscountCode(@RequestBody BasketDto basket, @PathVariable Long basketId) {
        if (basket.getId() != basketId && basket.getId() != 0) {
            throw new BasketConflictException(basketId, basket.getId());
        }
        basketService.updateBasketDiscountCode(basket, basketId);
        return ResponseEntity.ok("You updated discount code successfully!");
    }


    @DeleteMapping("{basketId}/products/{productId}")
    public ResponseEntity<String> deleteProductFromBasket(@PathVariable Long basketId, @PathVariable Long productId) {
        basketService.deleteProductFromBasket(basketId, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("{basketId}/customers")
    public ResponseEntity<String> setDeliveryData(@PathVariable Long basketId, @Valid @RequestBody DeliveryDataDto deliveryData) {
        basketService.setDeliveryData(basketId, deliveryData);
        return ResponseEntity.ok("You added delivery data successfully!");
    }

    @GetMapping("/{basketId}/calculate")
    public BasketDto recalculateBasketTotalPrice(@PathVariable Long basketId){
       return basketService.recalculateBasketTotalPrice(basketId);
    }

}
