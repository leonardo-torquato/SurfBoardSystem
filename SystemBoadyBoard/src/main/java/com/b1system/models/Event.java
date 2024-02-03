package com.b1system.models;

import com.b1system.utils.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Event {

    //TODO: definir tamanho maximo de nome, descricao, etc

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "status", nullable = false) // Aberta, fechada, em progresso, etc
    @Enumerated(EnumType.STRING)
    private Status status;

}
