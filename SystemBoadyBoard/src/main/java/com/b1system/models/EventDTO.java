package com.b1system.models;

import com.b1system.utils.EventStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    //private EventStatus status;

}
