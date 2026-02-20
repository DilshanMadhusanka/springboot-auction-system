package com.dilshan.auction_system.exception;

public class InvalidBidException extends RuntimeException {
    public InvalidBidException(String message){
        super(message);
    }
}