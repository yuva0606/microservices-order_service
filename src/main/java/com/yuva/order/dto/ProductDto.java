package com.yuva.order.dto;


public record ProductDto(
    String id,
    String name,
    String description,
    String category,
    Double price
) {
}
