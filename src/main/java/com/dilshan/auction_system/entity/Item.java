package com.dilshan.auction_system.entity;

import com.dilshan.auction_system.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal currentHighestBid;
    private LocalDateTime auctionEndTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN; // ← Add this back

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Bid> bids = new ArrayList<>();

    public boolean isExpired() {
        return auctionEndTime.isBefore(LocalDateTime.now());
    }
}