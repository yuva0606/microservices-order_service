package com.yuva.order.dto;

public record StockRequest(
    String productId,
    Integer quantity
) {
}
