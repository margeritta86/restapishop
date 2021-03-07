package com.orka.restapishop.model;

import com.orka.restapishop.dto.DeliveryDataDto;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Table(name="delivery_datas")
public class DeliveryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Address address;
    private String firstName;
    private String lastName;
    private String email;

    public DeliveryData() {
    }

    public DeliveryDataDto mapToDto() {
        return DeliveryDataDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .city(address.getCity())
                .street(address.getStreet())
                .buildingNumber(address.getBuildingNumber())
                .flatNumber(address.getFlatNumber())
                .postCode(address.getPostCode())
                .country(address.getCountry())
                .build();
    }

    public DeliveryData(Address address, String firstName, String lastName, String email) {
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "DeliveryData{" +
                "id=" + id +
                ", address=" + address +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryData that = (DeliveryData) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
