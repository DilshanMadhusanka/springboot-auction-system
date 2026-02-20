package com.dilshan.auction_system.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BidRequest {
    private String bidderName;
    private BigDecimal bidAmount;
}
