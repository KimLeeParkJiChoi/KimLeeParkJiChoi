package com.sparta.spangeats.domain.store.dto;

import com.sparta.spangeats.domain.store.entity.Store;

import java.time.LocalTime;

public record StoreSearchDto(
        String name,
        LocalTime openTime,
        LocalTime closeTime,
        Long minOrderPrice
) {
    public static StoreSearchDto from(Store store) {
        return new StoreSearchDto(
                store.getName(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderPrice()
        );
    }
}
