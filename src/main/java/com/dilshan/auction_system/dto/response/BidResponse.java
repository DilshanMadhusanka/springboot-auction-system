package com.dilshan.auction_system.dto.response;

import com.dilshan.auction_system.entity.Bid;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BidResponse {
    private Long id;
    private String bidderName;
    private BigDecimal bidAmount;
    private LocalDateTime bidTime;

    public BidResponse(Bid bid) {
        this.id = bid.getId();
        this.bidderName = bid.getBidderName();
        this.bidAmount = bid.getBidAmount();
        this.bidTime = bid.getBidTime();
    }
}