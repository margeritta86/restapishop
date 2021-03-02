package com.orka.restapishop.model;

import com.orka.restapishop.dto.BasketDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "basket")
    private Customer customer;
    @ManyToMany
    private List<Product> products;
    private int discount;


    public Basket() {
        products = new ArrayList<>();
    }

    public Basket(Customer customer) {
        this.customer = customer;
        products = new ArrayList<>();
    }

    public BasketDto mapToDto() {
        return BasketDto.builder()
                .id(id)
                .customerId(customer.getId())
                .products(products.stream()
                        .map(Product::mapToDto)
                        .collect(Collectors.toList()))
                .discount(discount)
                .build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void addProduct(Product product) {
        products.add(product);
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
                ", discount=" + discount +
                '}';
    }
}
