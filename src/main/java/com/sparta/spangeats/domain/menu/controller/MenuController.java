package com.sparta.spangeats.domain.menu.controller;

import com.sparta.spangeats.domain.menu.dto.request.MenuRequestDto;
import com.sparta.spangeats.domain.menu.dto.response.MenuResponseDto;
import com.sparta.spangeats.domain.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/{storeId}")
    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto requestDto) {
        MenuResponseDto responseDto = menuService.createMenu(storeId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<List<MenuResponseDto>> getMenusByStoreId(@PathVariable Long storeId) {
        List<MenuResponseDto> menus = menuService.getMenusByStoreId(storeId);
        return ResponseEntity.ok(menus);
    }

    @PutMapping("/{storeId}/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable Long storeId,
                                                      @PathVariable Long menuId,
                                                      @RequestBody MenuRequestDto requestDto) {
        MenuResponseDto responseDto = menuService.updateMenu(storeId, menuId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{storeId}/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long storeId,
                                           @PathVariable Long menuId) {
        menuService.deleteMenu(storeId, menuId);
        return ResponseEntity.noContent().build();
    }
}
