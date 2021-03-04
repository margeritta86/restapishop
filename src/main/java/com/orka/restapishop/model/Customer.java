package com.orka.restapishop.model;

import com.orka.restapishop.dto.CustomerDto;
import com.orka.restapishop.dto.DeliveryDataDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private DeliveryData deliveryData;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Basket basket;
    @OneToMany
    private List<Order> orders;


    public Customer() {
        orders = new ArrayList<>();
    }

    public Customer(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
        orders = new ArrayList<>();
    }


    public CustomerDto mapToDto() {
        return CustomerDto.builder()
                .id(id)
                .deliveryData(deliveryData == null ? DeliveryDataDto.builder().build() : deliveryData.mapToDto())
                .basket(basket.mapToDto())
                .orders(orders.stream()
                        .map(Order::mapToDto)
                        .collect(Collectors.toList()))
                .build();
    }


    public long getId() {
        return id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
    }

    public void addOrderToList(Order order) {
        orders.add(order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", deliveryData=" + deliveryData +
                ", basket=" + basket +
                ", orders=" + orders +
                '}';
    }
}
