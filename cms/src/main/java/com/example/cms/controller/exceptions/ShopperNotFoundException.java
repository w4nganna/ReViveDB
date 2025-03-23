package com.example.cms.controller.exceptions;

public class ShopperNotFoundException extends RuntimeException {
    public ShopperNotFoundException(String shopperId) {
        super("Could not find shopper" + shopperId);
}}
