package com.orka.restapishop.model;

import com.orka.restapishop.dto.DiscountCodeDto;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "discount_codes")
public class DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double value;
    private LocalDate expireDate;

    public DiscountCode() {
    }

    public DiscountCode(String name, double value, LocalDate expireDate) {
        this.name = name;
        this.value = value;
        this.expireDate = expireDate;
    }

    public DiscountCodeDto mapToDto() {
        return DiscountCodeDto.builder()
                .id(id)
                .name(name)
                .value(value)
                .expireDate(expireDate)
                .build();

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCode that = (DiscountCode) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DiscountCode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", expireDate=" + expireDate +
                '}';
    }
}
