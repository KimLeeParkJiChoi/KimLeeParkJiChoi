package com.sparta.spangeats.domain.order.dto;

import com.sparta.spangeats.domain.order.entity.DeliveryType;
import com.sparta.spangeats.domain.order.entity.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderSaveRequest(
        @NotNull(message = "storeId가 비어있습니다.")
        Long storeId,
        @NotNull(message = "addressId가 비어있습니다.")
        Long addressId,
        String riderRequest,
        String storeRequest,
        PaymentType paymentType,
        DeliveryType deliveryType,
        List<MenuOrders> orders,
        Integer totalPrice
) {
}