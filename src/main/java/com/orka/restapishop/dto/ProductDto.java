package com.orka.restapishop.dto;


import com.fasterxml.jackson.annotation.JsonView;
import com.orka.restapishop.model.Rate;
import com.orka.restapishop.view.View;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


@Builder
@Data
public class ProductDto {


    private Long id;
    @JsonView({View.MinimalDetails.class, View.Details.class})
    private String name;
    @JsonView({View.MinimalDetails.class})
    private String imageUrl;
    @JsonView({View.Details.class})
    private String details;
    @JsonView({View.MinimalDetails.class})
    private Double price;
    @JsonView({View.Details.class})
    private Rate rate;
    @JsonView({View.Details.class})
    private List<AttributeDto> attributes;
    @JsonView({View.Details.class})
    private long amount;
    @JsonView({View.Details.class})
    private boolean available;



}
