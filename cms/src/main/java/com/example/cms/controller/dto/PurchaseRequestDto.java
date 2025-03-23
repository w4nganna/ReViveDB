package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDto {
    private Long id;
    private String shopperId;
    private Long productId;
    private int quantity;
}