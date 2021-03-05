package com.orka.restapishop.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryDataDto {

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
    @Size(min = 3, message = "Invalid first name - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;
    @Size(min = 3, message = "Invalid last name - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastName;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid mail format!")
    private String email;

}
