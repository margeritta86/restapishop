package com.orka.restapishop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BasketDto {


    private long id;
    private long customerId;
    private List<ProductDto> products;
    private int discount;

}
