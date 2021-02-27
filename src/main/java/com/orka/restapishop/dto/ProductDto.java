package com.orka.restapishop.dto;


import com.orka.restapishop.model.Attribute;
import com.orka.restapishop.model.Rate;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductDto {

    private Long id;
    private String name;
    private String imageUrl;
    private String details;
    private Rate rate;
    private List<Attribute> attributes;
}
