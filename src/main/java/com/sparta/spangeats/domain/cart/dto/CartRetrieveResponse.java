package com.sparta.spangeats.domain.cart.dto;

import com.sparta.spangeats.domain.member.entity.Member;
import lombok.Builder;

import java.util.List;

@Builder
public record CartRetrieveResponse(
        Long memberId,
        List<CartItemResponse> cartItems
) {
    public static CartRetrieveResponse of(List<CartItemResponse> cartItemResponses, Member member) {
        return CartRetrieveResponse.builder()
                .memberId(member.getId())
                .cartItems(cartItemResponses)
                .build();
    }
}
