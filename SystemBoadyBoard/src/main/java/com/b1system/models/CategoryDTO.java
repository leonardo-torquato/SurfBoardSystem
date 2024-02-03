package com.b1system.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private Integer event_id;
    private String description;
    private Integer slots;
    private BigDecimal price;
    private BigDecimal memberPrice;

}
