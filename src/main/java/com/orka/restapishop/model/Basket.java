package com.orka.restapishop.model;

import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.excepiton.ProductNotFoundException;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ElementCollection
    private Map<Product, Integer> products;
    private String discountCode;
    private BigDecimal totalPrice;
    @OneToOne
    private DeliveryData deliveryData;


    public Basket() {
        products = new HashMap<>();
    }

    public Basket(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
        products = new HashMap<>();
    }

    public BasketDto mapToDto() {

        return BasketDto.builder()
                .id(id)
                .deliveryData(deliveryData == null ? DeliveryDataDto.builder().build() : deliveryData.mapToDto())
                .products(products.entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue)))
                .discountCode(discountCode)
                .totalPrice(totalPrice)
                .build();
    }

    public long getId() {
        return id;
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

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
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

    public void deleteProduct(Product product) {
        if (!products.containsKey(product)) {
            throw new ProductNotFoundException(product.getId());
        }
        products.remove(product);

    }

    public BigDecimal getTotalPrice() {

        for (Map.Entry<Product, Integer> productIntegerEntry : products.entrySet()) {
                BigDecimal price = productIntegerEntry.getKey().getPrice();
                BigDecimal amountOfProduct = new BigDecimal(productIntegerEntry.getValue());
                BigDecimal totalPriceOfProduct =  price.multiply(amountOfProduct);
                totalPrice.add(totalPriceOfProduct);
        }

        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
                ", products=" + products +
                ", discountCode='" + discountCode + '\'' +
                ", totalPrice=" + totalPrice +
                ", deliveryData=" + deliveryData +
                '}';
    }
}
