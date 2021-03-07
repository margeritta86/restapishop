package com.orka.restapishop.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
public class DiscountCodeDto {

    private long id;
    private String name;
    private double value;
    private LocalDate expireDate;

}
