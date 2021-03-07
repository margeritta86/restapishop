package com.orka.restapishop.model;

import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.dto.DiscountCodeDto;
import com.orka.restapishop.excepiton.ProductNotFoundException;
import com.orka.restapishop.excepiton.RequestedAmountException;

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
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Product, Integer> products;
    @ManyToOne
    private DiscountCode discountCode;
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
                .discountCode(discountCode == null ? DiscountCodeDto.builder().build().getName() : discountCode.mapToDto().getName())
                .totalPrice(calculateTotalPrice())
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
    }

    public void addProduct(Product product, Integer amount) {

        int actualAmount;
        if (products.get(product) == null) {
            actualAmount = 0;
        } else {
            actualAmount = products.get(product);
        }
        if (actualAmount+amount > product.getAmount() || amount <= 0) {
            throw new RequestedAmountException(createAmountMessageException(product.getId(), amount));
        }
        products.put(product, actualAmount + amount);
        calculateTotalPrice();
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    private String createAmountMessageException(long idProduct, int amount) {
        String message = "";

        if (amount < 0) {
            message = "Requested amount: " + amount + " cannot be a negative number";
        } else if (amount == 0) {
            message = "Requested amount: " + amount + " cannot be 0 value";
        } else {
            message = "Inventory value: " + amount + " exceeded ";
        }

        message += "for product id: " + idProduct;

        return message;
    }


    public void updateProduct(Product product, Integer amount) {
        if (!products.containsKey(product)) {
            throw new ProductNotFoundException(product.getId());
        }
        products.put(product, amount);
        calculateTotalPrice();
    }

    public void deleteProduct(Product product) {
        if (!products.containsKey(product)) {
            throw new ProductNotFoundException(product.getId());
        }
        products.remove(product);

    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = new BigDecimal("0");
        for (Map.Entry<Product, Integer> productIntegerEntry : products.entrySet()) {
            BigDecimal price = productIntegerEntry.getKey().getPrice();
            BigDecimal amountOfProduct = new BigDecimal(productIntegerEntry.getValue());
            BigDecimal totalPriceOfProduct = price.multiply(amountOfProduct);
            totalPrice = totalPrice.add(totalPriceOfProduct);
        }
        return applyDiscount(totalPrice);
    }

    private BigDecimal applyDiscount(BigDecimal withoutDiscount) {
        if (discountCode == null) {
            return withoutDiscount;
        }
        return BigDecimal.valueOf(1 -discountCode.getValue()).multiply(withoutDiscount);
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
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
                ", deliveryData=" + deliveryData +
                '}';
    }

}
