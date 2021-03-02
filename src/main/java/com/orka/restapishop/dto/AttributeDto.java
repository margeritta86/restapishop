package com.orka.restapishop.dto;

import com.orka.restapishop.model.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AttributeDto {


    private Long id;
    private String name;
    private String value;


}
