package com.dilshan.auction_system.controller;

import com.dilshan.auction_system.dto.request.ItemRequest;
import com.dilshan.auction_system.dto.response.ItemResponse;
import com.dilshan.auction_system.entity.Bid;
import com.dilshan.auction_system.entity.Item;
import com.dilshan.auction_system.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemResponse createItem(@RequestBody ItemRequest request){
        Item item = Item.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startingPrice(request.getStartingPrice())
                .auctionEndTime(request.getAuctionEndTime())
                .build();
        return new ItemResponse(itemService.createItem(item));
    }

    @GetMapping
    public List<ItemResponse> getAllActiveItems(){
        return itemService.getAllActiveItems().stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ItemResponse getItem(@PathVariable Long id){
        return new ItemResponse(itemService.getItemById(id));
    }

    @GetMapping("/{id}/winner")
    public Bid getWinner(@PathVariable Long id){
        return itemService.getWinner(id);
    }
}