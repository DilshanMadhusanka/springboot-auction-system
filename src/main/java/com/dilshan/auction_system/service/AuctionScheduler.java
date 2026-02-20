package com.dilshan.auction_system.service;

import com.dilshan.auction_system.entity.Item;
import com.dilshan.auction_system.enums.Status;
import com.dilshan.auction_system.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionScheduler {

    private final ItemRepository itemRepository;

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void closeExpiredAuctions() {
        List<Item> expiredItems =
                itemRepository.findByStatusAndAuctionEndTimeBefore(Status.OPEN, LocalDateTime.now());

        for (Item item : expiredItems) {
            item.setStatus(Status.CLOSED);
            itemRepository.save(item);
            System.out.println("Auction automatically closed for item: " + item.getTitle());
        }
    }
}