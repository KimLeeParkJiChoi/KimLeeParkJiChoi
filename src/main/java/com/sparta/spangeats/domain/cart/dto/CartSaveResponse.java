package com.sparta.spangeats.domain.cart.dto;

import com.sparta.spangeats.domain.cart.entity.Cart;
import lombok.Builder;

@Builder
public record CartSaveResponse(
        Long menuId,
        Long quantity,
        Long totalPrice
) {
    public static CartSaveResponse from(Cart cart) {
        return CartSaveResponse.builder()
                .menuId(cart.getMenu().getId())
                .quantity(cart.getQuantity())
                .totalPrice(cart.getPrice() * cart.getQuantity())
                .build();
    }
}
