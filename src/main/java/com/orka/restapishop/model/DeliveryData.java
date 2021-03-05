package com.orka.restapishop.model;

import com.orka.restapishop.dto.DeliveryDataDto;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name="delivery_datas")
public class DeliveryData {

    @Id
    private long id;
    @OneToOne
    private Address address;
    @Size(min = 3, message = "Invalid first name - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;
    @Size(min = 3, message = "Invalid last name - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastName;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid mail format!")
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
