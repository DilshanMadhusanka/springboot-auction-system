package com.dilshan.auction_system.repository;

import com.dilshan.auction_system.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<Bid> findTopByItemIdOrderByBidAmountDesc(Long itemId);
}