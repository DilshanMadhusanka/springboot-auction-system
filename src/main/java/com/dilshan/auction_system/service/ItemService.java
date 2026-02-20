package com.dilshan.auction_system.service;

import com.dilshan.auction_system.entity.Bid;
import com.dilshan.auction_system.entity.Item;
import com.dilshan.auction_system.enums.Status;
import com.dilshan.auction_system.exception.ResourceNotFoundException;
import com.dilshan.auction_system.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // Create a new auction item
    public Item createItem(Item item) {
        item.setStatus(Status.OPEN); // ensure new item starts as OPEN
        return itemRepository.save(item);
    }

    // Get all active (OPEN) auction items
    public List<Item> getAllActiveItems() {
        return itemRepository.findByStatus(Status.OPEN);
    }

    // Get a single item by ID and automatically close expired auctions
    public Item getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id " + id));

        // Close auction if expired
        if (item.getStatus() == Status.OPEN && item.getAuctionEndTime().isBefore(LocalDateTime.now())) {
            item.setStatus(Status.CLOSED);
            itemRepository.save(item);
        }
        return item;
    }

    // Get the winning bid for a closed auction
    public Bid getWinner(Long itemId) {
        Item item = getItemById(itemId);
        if (item.getStatus() == Status.OPEN) {
            throw new RuntimeException("Auction is still open");
        }
        return item.getBids().stream()
                .max((b1, b2) -> b1.getBidAmount().compareTo(b2.getBidAmount()))
                .orElse(null);
    }

    // Close all expired auctions in a list
    public void closeExpiredAuctions(List<Item> items) {
        for (Item item : items) {
            if (item.getStatus() == Status.OPEN && item.getAuctionEndTime().isBefore(LocalDateTime.now())) {
                item.setStatus(Status.CLOSED);
                itemRepository.save(item);
            }
        }
    }
}