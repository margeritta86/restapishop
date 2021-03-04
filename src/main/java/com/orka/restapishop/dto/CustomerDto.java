package com.orka.restapishop.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CustomerDto {

    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private BasketDto basket;
    private List<OrderDto> orders;


}
