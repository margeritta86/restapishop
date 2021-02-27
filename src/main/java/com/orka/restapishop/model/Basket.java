package com.orka.restapishop.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "basket")
    private List<Product> products;


    public Basket() {
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
}
