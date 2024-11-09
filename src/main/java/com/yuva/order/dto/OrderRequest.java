package com.yuva.order.dto;

import org.springframework.stereotype.Component;

public record OrderRequest(
    String username,
    String productId,
    int quantity
) {
}
