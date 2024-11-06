package com.sparta.spangeats.domain.store.dto;

import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.store.entity.Store;

import java.time.LocalTime;
import java.util.List;

public record StoreIdSearchDto(
    String name,
    LocalTime openTime,
    LocalTime closeTime,
    Long minOrderPrice,
    String phoneNumber,
    String address,
    String notice, // 공지 추가
    List<Menu> menus

){  public static StoreIdSearchDto from(Store store) {
    return new StoreIdSearchDto(
            store.getName(),
            store.getOpenTime(),
            store.getCloseTime(),
            store.getMinOrderPrice(),
            store.getPhoneNumber(),
            store.getAddress(),
            store.getNotice(), // 공지 추가
            store.getMenus() // 연관된 메뉴들
    );
}
}

