package com.yuva.order.event;

public record ReduceStockEvent(
    String productId,
    Integer quantity
) {
}
