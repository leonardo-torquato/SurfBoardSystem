package com.b1system.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDTO {

    private Integer eventId;
    private Set<Integer> categoriesId;
    private String cpf;
    private String email;
    private String fullName;
    private String nickname;
    private LocalDate birthDate;
    private String siglaFederacao;
    private byte[] picture;

}
