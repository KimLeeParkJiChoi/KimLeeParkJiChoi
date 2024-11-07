package com.sparta.spangeats.domain.order.dto;

import com.sparta.spangeats.domain.order.entity.OrderStatus;

public record OrderUpdateStatusRequest(
        OrderStatus status
) {
}
