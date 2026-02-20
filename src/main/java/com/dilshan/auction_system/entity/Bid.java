package com.dilshan.auction_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bidderName;
    private BigDecimal bidAmount;

    @Builder.Default
    private LocalDateTime bidTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}