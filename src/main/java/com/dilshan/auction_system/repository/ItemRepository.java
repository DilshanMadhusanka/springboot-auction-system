package com.dilshan.auction_system.repository;

import com.dilshan.auction_system.entity.Item;
import com.dilshan.auction_system.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(Status status);

    List<Item> findByAuctionEndTimeBeforeAndStatus(
            LocalDateTime time, Status status);
}

