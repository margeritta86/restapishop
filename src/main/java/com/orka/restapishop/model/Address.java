package com.orka.restapishop.model;




import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="addresses")
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Size(min = 3, message = "Invalid city - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String city;
    @Size(min = 3, message = "Invalid street - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String street;
    @Size(min = 1, message = "Invalid building number - It has to be at least 3 characters !")
    @Pattern(regexp = "[1-9]+")
    private String buildingNumber;
    private String flatNumber;
    @Size(min = 3, message = "Invalid city - It has to be at least 3 characters !")
    private String postCode;
    @Size(min = 4, message = "Invalid country - It has to be at least 4 characters !")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String country;

    public Address() {
    }

    public Address(String city, String street, String buildingNumber, String flatNumber, String postCode, String country) {
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
        this.postCode = postCode;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
