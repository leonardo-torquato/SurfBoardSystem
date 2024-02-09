package com.b1system.models.createDtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCreateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

}
