package com.orka.restapishop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class BasketDto {


    private long id;
    private long customerId;
    private Map <Long,Integer> products;
    private String discountCode;

}
