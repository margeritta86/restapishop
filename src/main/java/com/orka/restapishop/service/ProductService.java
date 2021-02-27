package com.orka.restapishop.service;

import com.orka.restapishop.dto.ProductDto;
import com.orka.restapishop.model.Product;
import com.orka.restapishop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    private ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostConstruct
    public void fillDatabase() {

        if (productRepository.count() > 0) {
            return;
        }
        Product prod1 = new Product("Ham", new BigDecimal(5), "imgUrlO");
        Product prod2 = new Product("Mushrooms", new BigDecimal(8), "imgUrl2");
        Product prod3 = new Product("Pineapple", new BigDecimal(4), "imgUrl3");

        productRepository.save(prod1);
        productRepository.save(prod2);
        productRepository.save(prod3);
    }

    public List<ProductDto> getAllProducts(){
       return productRepository.findAll().stream()
                .map(product->ProductDto.builder()
                .id(product.getId())
                .details(product.getDetails())
                .imageUrl(product.getImageUrl())
                .name(product.getName())
                .rate(product.getRate())
                .attributes(product.getAttributes())
                .build())
                .collect(Collectors.toList());


    }
}
