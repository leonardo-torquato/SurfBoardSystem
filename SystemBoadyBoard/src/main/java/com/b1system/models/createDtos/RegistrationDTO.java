package com.b1system.models.createDtos;

import com.b1system.utils.ValidEstadoSigla;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDTO {

    //TODO: definir tamanho minimo e maximo para cada campo

    @NotEmpty
    private String username;

    @NotEmpty
    private String notEncodedPassword;

    @NotEmpty
    @CPF
    private String cpf;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String nickname;

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    @NotEmpty
    @ValidEstadoSigla
    private String siglaFederacao;


}
