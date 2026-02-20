package com.dilshan.auction_system.dto.response;

import com.dilshan.auction_system.entity.Item;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal currentHighestBid;
    private LocalDateTime auctionEndTime;
    private String status;

    public ItemResponse(Item item){
        this.id = item.getId();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.startingPrice = item.getStartingPrice();
        this.currentHighestBid = item.getCurrentHighestBid();
        this.auctionEndTime = item.getAuctionEndTime();
        this.status = item.getStatus().name();
    }
}