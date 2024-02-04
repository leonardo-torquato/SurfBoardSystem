package com.b1system.models;

import com.b1system.utils.EventStatus;
import com.b1system.utils.SubscriptionStatus;
import com.b1system.utils.UnidadeFederacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event eventId;

    @ManyToMany
    @JoinTable(name = "subscription_categories",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @Column(name = "CPF", nullable = false, unique = true)
    @CPF
    private String cpf;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "federation", nullable = false)
    private UnidadeFederacao federation;

    //TODO: definir tamanho maximo
    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    private SubscriptionStatus status; //pending, paid, expired

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;


}
