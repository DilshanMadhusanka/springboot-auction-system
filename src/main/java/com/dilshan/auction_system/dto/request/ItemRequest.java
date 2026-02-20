package com.dilshan.auction_system.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemRequest {
    private String title;
    private String description;
    private BigDecimal startingPrice;
    private LocalDateTime auctionEndTime;
}
