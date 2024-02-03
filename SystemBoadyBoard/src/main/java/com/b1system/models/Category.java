package com.b1system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//TODO: implementar alguma estratégia para evitar overbooking

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event competitionId;

    @Column(name = "description")
    private String description;

    @Column(name = "remaining_slots")
    private Integer remainingSlots;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "member_price")
    private BigDecimal memberPrice;

    public boolean hasAvailableSlots() {
        return remainingSlots > 0;
    }

    public void deductSlot() {
        if (hasAvailableSlots()) {
            remainingSlots--;
        } else {
            throw new IllegalStateException("Não há mais vagas para essa categoria.");
        }
    }

}
