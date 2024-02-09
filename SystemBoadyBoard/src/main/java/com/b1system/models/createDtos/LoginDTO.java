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
public class LoginDTO {

    //TODO: definir tamanho minimo e maximo para os campos

    @NotEmpty
    private String username;

    @NotEmpty
    private String notEncodedPassword;

}