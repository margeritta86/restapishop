package com.orka.restapishop.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderDto {

    private long id;
    private BasketDto basket;
    private boolean placedOrder;



}
