package com.orka.restapishop.model;

import com.orka.restapishop.dto.AttributeDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.dto.ProductDto;
import com.orka.restapishop.excepiton.RequestedAmountException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long amount;
    private String name;
    @Column(scale = 2)
    private BigDecimal price;
    private String imageUrl;
    private String details;
    private Rate rate;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_id")
    private List<Attribute> attributes;
    private boolean available =false;


    public Product() {
    }

    public Product(String name, BigDecimal price, String imageUrl, long amount) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.available = isAvailable();
    }

    public ProductDto mapToDto() {
        return ProductDto.builder()
                .id(id)
                .amount(amount)
                .attributes(attributes.stream()
                        .map(Attribute::mapToDto)
                        .collect(Collectors.toList()))
                .price(price)
                .details(details)
                .name(name)
                .imageUrl(imageUrl)
                .rate(rate)
                .available(available)
                .build();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }



    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public boolean isAvailable() {
        this.available = amount > 0;
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void decreaseAmount(int count){
        if (amount - count < 0) {
            throw new RequestedAmountException("The amount of the product is big "+ id);//todo zmienić
        }
        amount -= count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", details='" + details + '\'' +
                ", rate=" + rate +
                ", isAvailable=" + available +
                ", attributes=" + attributes +
                '}';
    }
}
