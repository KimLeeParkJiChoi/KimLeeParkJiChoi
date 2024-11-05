package com.sparta.spangeats.domain.menu.dto.response;

public record MenuResponseDto(Long id,
                              String name,
                              Integer price,
                              String status,
                              String description){ // 메뉴 설명

}
