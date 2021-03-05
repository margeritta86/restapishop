package com.orka.restapishop.service;

import com.orka.restapishop.dto.AttributeDto;
import com.orka.restapishop.dto.ProductDto;
import com.orka.restapishop.excepiton.ProductNotFoundException;
import com.orka.restapishop.excepiton.RequestedValueException;
import com.orka.restapishop.model.Product;
import com.orka.restapishop.repository.AttributeRepository;
import com.orka.restapishop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    private ProductRepository productRepository;
    private AttributeRepository attributeRepository;

    public ProductService(ProductRepository productRepository, AttributeRepository attributeRepository) {
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
    }


    @PostConstruct
    public void fillDatabase() {

        if (productRepository.count() > 0) {
            return;
        }
        Product prod1 = new Product("Ham", new BigDecimal("5.07"), "imgUrl1", 10);
        Product prod2 = new Product("Mushrooms", new BigDecimal("8.08"), "imgUrl2", 10);
        Product prod3 = new Product("Pineapple", new BigDecimal("4.05"), "imgUrl3", 10);

        productRepository.save(prod1);
        productRepository.save(prod2);
        productRepository.save(prod3);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(Product::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)).mapToDto();

    }

    public List<AttributeDto> getAllAttributes() {
        return attributeRepository.findAll().stream()
                .map(attribute -> AttributeDto.builder()
                        .id(attribute.getId())
                        .name(attribute.getName())
                        .value(attribute.getValue())
                        .build())
                .collect(Collectors.toList());
    }


    public Collection<ProductDto> getProductsByMinPrice(Double price, String minOrmax) {
        Collection<Product> products;
        if (minOrmax.equals("min")) {
            products = productRepository.findProductsByMinPrice(price);

        } else if (minOrmax.equals("max")) {
            products = productRepository.findProductsByMaxPrice(price);

        } else {
            throw new RequestedValueException(minOrmax);
        }

        return products.stream()
                .map(Product::mapToDto)
                .collect(Collectors.toList());

    }

    public List<ProductDto> getProductsByKeyword(String keyword) {
        String keywordLower = keyword.toLowerCase();
        return getAllProducts().stream()
                .filter(productDto -> productDto.getName().toLowerCase().contains(keywordLower))
                .collect(Collectors.toList());

    }
}
