package com.b1system.models;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDTO {

    //TODO: mensagens de erro

    @NotNull
    private Integer eventId;

    @NotEmpty
    @ElementCollection
    private Set<Integer> categoriesId;

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

    @NotNull
    private LocalDate birthDate;

    @NotEmpty
    private String siglaFederacao;

    private byte[] picture;

}
