package com.orka.restapishop.dto;


import com.orka.restapishop.model.Basket;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private BasketDto basket;


}
