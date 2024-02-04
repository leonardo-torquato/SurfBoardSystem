package com.b1system.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Integer eventId;

    @NotEmpty
    private String description;

    @NotNull
    private Integer slots;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal memberPrice;

}
