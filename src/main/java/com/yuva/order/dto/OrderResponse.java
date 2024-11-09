package com.yuva.order.dto;

public record OrderResponse(
    Long id,
    String username,
    String productId,
    String productName,
    int quantity,
    String status
) {
}
