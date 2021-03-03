package com.orka.restapishop.model;

import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.excepiton.ProductNotFoundException;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(mappedBy = "basket")
    private Customer customer;
    @ElementCollection
    private Map<Product, Integer> products;
    private String discountCode;


    public Basket() {
        products = new HashMap<>();
    }

    public Basket(Customer customer) {
        this.customer = customer;
        products = new HashMap<>();
    }

    public BasketDto mapToDto() {

        return BasketDto.builder()
                .id(id)
                .customerId(customer.getId())
                .products(products.entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue)))
                .discountCode(discountCode)
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void addProduct(Product product, Integer amount) {
        if (products.containsKey(product)) {

            products.put(product, products.get(product) + amount);
        } else {
            products.put(product, amount);
        }
    }

    public void updateProduct(Product product, Integer amount) {
        if (!products.containsKey(product)) {
            throw new ProductNotFoundException(product.getId());
        }
        products.put(product, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(id, basket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", customer=" + customer.getId() +
                ", products=" + products +
                ", discount=" + discountCode +
                '}';
    }
}
