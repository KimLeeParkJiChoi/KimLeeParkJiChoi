package com.sparta.spangeats.domain.menu.dto.response;

import com.sparta.spangeats.domain.menu.entity.Menu;

public record MenuDto(
        String name,
        Integer price,
        String description
) {
    public static MenuDto from(Menu menu) {
        return new MenuDto(
                menu.getName(),
                menu.getPrice(),
                menu.getDescription()
        );
    }
}