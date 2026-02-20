package com.dilshan.auction_system.service;

import com.dilshan.auction_system.entity.Bid;
import com.dilshan.auction_system.entity.Item;
import com.dilshan.auction_system.enums.Status;
import com.dilshan.auction_system.exception.InvalidBidException;
import com.dilshan.auction_system.repository.BidRepository;
import com.dilshan.auction_system.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final ItemRepository itemRepository;

    public Bid placeBid(Long itemId, Bid bid) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new InvalidBidException("Item not found"));

        // Check auction status
        if (item.getStatus() == Status.CLOSED || item.isExpired()) {
            throw new InvalidBidException("Auction is closed");
        }

        // Check bid amount
        BigDecimal currentHighest = item.getCurrentHighestBid() != null
                ? item.getCurrentHighestBid()
                : item.getStartingPrice();

        if (bid.getBidAmount().compareTo(currentHighest) <= 0) {
            throw new InvalidBidException("Bid must be higher than current highest bid");
        }

        // Save bid
        bid.setItem(item);
        item.setCurrentHighestBid(bid.getBidAmount());
        item.getBids().add(bid);

        return bidRepository.save(bid);
    }
}
