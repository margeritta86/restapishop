package com.orka.restapishop.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.orka.restapishop.dto.ProductDto;
import com.orka.restapishop.service.ProductService;
import com.orka.restapishop.view.View;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @JsonView({View.MinimalDetails.class})
    @GetMapping("/products")
    public List<ProductDto> getAll() {
        return productService.getAllProducts();
    }

    @JsonView({View.Details.class})
    @GetMapping("/products/{id}")
    public ProductDto getDetailsOfProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @JsonView({View.MinimalDetails.class})
    @GetMapping("/products/byPrice")
    public Collection<ProductDto> getProductsByMinPrice(Double price, String minOrMax){
       return productService.getProductsByMinPrice(price,minOrMax);
    }

    @JsonView({View.MinimalDetails.class})
    @GetMapping("/products/byKeyword")
    public List<ProductDto> getProductsByKeyword( String keyword){//zrobić na repozytorium metodę ??
        return productService.getProductsByKeyword(keyword);
    }

}
