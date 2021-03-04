package com.orka.restapishop.model;

import com.orka.restapishop.dto.OrderDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Basket basket;
    private BigDecimal totalPrice;
    private boolean placedOrder;


    public Order() {
        placedOrder = false;
    }

    public Order(Basket basket) {
        this.basket = basket;
        placedOrder = false;
    }

    public OrderDto mapToDto() {
        return OrderDto.builder()
                .id(id)
                .basket(basket.mapToDto())
                .placedOrder(placedOrder)
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPlacedOrder() {
        return placedOrder;
    }

    public void setPlacedOrder(boolean placedOrder) {
        this.placedOrder = placedOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", basket=" + basket.getId() +
                '}';
    }
}
