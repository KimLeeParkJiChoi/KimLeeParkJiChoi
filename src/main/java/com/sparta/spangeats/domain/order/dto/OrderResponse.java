package com.sparta.spangeats.domain.order.dto;

import com.sparta.spangeats.domain.order.entity.DeliveryType;
import com.sparta.spangeats.domain.order.entity.Order;
import com.sparta.spangeats.domain.order.entity.OrderStatus;
import com.sparta.spangeats.domain.order.entity.PaymentType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        String storeName,
        String memberName,
        String address,
        OrderStatus status,
        String riderRequest,
        String storeRequest,
        PaymentType paymentType,
        DeliveryType deliveryType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer totalPrice
) {
    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .storeName(order.getStore().getName())
                .memberName(order.getMember().getNickname())
                .address(order.getAddress().getAddress())
                .status(order.getStatus())
                .riderRequest(order.getRiderRequest())
                .storeRequest(order.getStoreRequest())
                .paymentType(order.getPaymentType())
                .deliveryType(order.getDeliveryType())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
