package com.orka.restapishop.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;


@Builder
@Data
public class BasketDto {


    private long id;
    private DeliveryDataDto deliveryData;
    private Map <Long,Integer> products;//todo może wyświetlać nazwy produktów, a nie id?
    private String discountCode;
    private BigDecimal totalPrice;

}
