package com.dilshan.auction_system.controller;

import com.dilshan.auction_system.dto.request.BidRequest;
import com.dilshan.auction_system.dto.response.BidResponse;
import com.dilshan.auction_system.entity.Bid;
import com.dilshan.auction_system.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("/{itemId}/bids")
    public BidResponse placeBid(@PathVariable Long itemId, @RequestBody BidRequest request) {
        Bid bid = Bid.builder()
                .bidderName(request.getBidderName())
                .bidAmount(request.getBidAmount())
                .build();
        Bid savedBid = bidService.placeBid(itemId, bid);
        return new BidResponse(savedBid);
    }
}