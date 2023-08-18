package com.ecommerce.app.models.dto.product;

public record CreateProductDto(String name, String description, Double price, Integer stock) {
}
