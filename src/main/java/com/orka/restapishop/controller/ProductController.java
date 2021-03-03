package com.orka.restapishop.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.orka.restapishop.dto.ProductDto;
import com.orka.restapishop.model.Product;
import com.orka.restapishop.repository.ProductRepository;
import com.orka.restapishop.service.ProductService;
import com.orka.restapishop.view.View;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;
    private ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
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

  /*  @GetMapping("/products/sort")
    public List<ProductDto> getProductsByPrice(){
        *//*return productRepository.findAllByPrice().stream()
                .map(Product::mapToDto)
                .collect(Collectors.toList());*//*

    }*/




}
