package com.orka.restapishop.service;


import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.ProductDto;
import com.orka.restapishop.model.Basket;
import com.orka.restapishop.model.Customer;
import com.orka.restapishop.model.Product;
import com.orka.restapishop.repository.BasketRepository;
import com.orka.restapishop.repository.CustomerRepository;
import com.orka.restapishop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;


@Service
public class BasketService {

    private BasketRepository basketRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }


    public void saveProductToBasket(long basketId, long productId) {

        Product product = productRepository.findById(productId).get(); //todo get
        Basket basket = basketRepository.findById(basketId).get(); //todo get

        basket.addProduct(product);

        basketRepository.save(basket);


    }

    @PostConstruct
    public void fillDataBase() {

        if (basketRepository.count() > 0 && customerRepository.count() >0) {
            return;
        }
        Customer customer1 = new Customer("Marina", "Kaczor");
        Customer customer2 = new Customer("Masza", "Tau");
        Customer customer3 = new Customer("Nikola", "Thomson");

        Basket basket1 = new Basket();
        Basket basket2 = new Basket();
        Basket basket3 = new Basket();

        basketRepository.save(basket1);
        basketRepository.save(basket2);
        basketRepository.save(basket3);

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        customer1.setBasket(basket1);
        customer2.setBasket(basket2);
        customer3.setBasket(basket3);

        basket1.setCustomer(customer1);
        basket2.setCustomer(customer2);
        basket3.setCustomer(customer3);

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        basketRepository.save(basket1);
        basketRepository.save(basket2);
        basketRepository.save(basket3);


    }

    public BasketDto getBasketDtoById(Long id) {

       /* Basket bk = basketRepository.findById(id).orElseThrow();
        System.out.println(bk);
        System.out.println(bk.mapToDto());
        System.err.println(basketRepository.findById(id).stream().map(Basket::mapToDto).collect(Collectors.toList()));
        System.out.println("...");*/

        BasketDto basketDto = basketRepository.findById(id).stream()
                .map(basket -> BasketDto.builder()
                        .id(basket.getId())
                        .customerId(basket.getCustomer().getId())
                        .products(basket.getProducts().stream()
                                .map(Product::mapToDto)
                                .collect(Collectors.toList()))
                        .discount(basket.getDiscount())
                        .build())
                .findFirst().get();

        return basketDto;
    }


}
