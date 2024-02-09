package com.b1system.models.createDtos;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionCreateDTO {

    //TODO: mensagens de erro

    @NotNull
    private Integer eventId;

    @NotEmpty
    @ElementCollection
    private Set<Integer> categoriesId;

    @NotNull
    private Integer userId;

}
