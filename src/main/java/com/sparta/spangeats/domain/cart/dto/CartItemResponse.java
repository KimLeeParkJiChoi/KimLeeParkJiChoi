package com.sparta.spangeats.domain.cart.dto;

import com.sparta.spangeats.domain.cart.entity.Cart;
import lombok.Builder;

@Builder
public record CartItemResponse(
        Long cartItemId,
        Long menuId,
        String menuName,
        Long quantity,
        Long pricePerItem,
        Long totalPrice
) {
    public static CartItemResponse from(Cart cart) {
        return CartItemResponse.builder()
                .cartItemId(cart.getId())
                .menuId(cart.getMenu().getId())
                .menuName(cart.getMenu().getName())
                .quantity(cart.getQuantity())
                .pricePerItem(cart.getQuantity())
                .totalPrice(cart.getPrice() * cart.getQuantity())
                .build();
    }
}
