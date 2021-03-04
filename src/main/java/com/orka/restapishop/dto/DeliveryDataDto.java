package com.orka.restapishop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryDataDto {

    private String city;
    private String street;
    private String buildingNumber;
    private String flatNumber;
    private String postCode;
    private String country;
    private String firstName;
    private String lastName;
    private String email;

}
