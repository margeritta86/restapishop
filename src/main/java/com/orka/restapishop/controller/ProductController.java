package com.orka.restapishop.controller;


import com.orka.restapishop.dto.ProductDto;
import com.orka.restapishop.model.Product;
import com.orka.restapishop.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductDto> getAll(){
        return  productService.getAllProducts();
    }
}
