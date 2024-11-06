package com.sparta.spangeats.domain.menu.service;

import com.sparta.spangeats.domain.menu.dto.request.MenuRequestDto;
import com.sparta.spangeats.domain.menu.dto.response.MenuResponseDto;
import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.menu.entity.MenuStatus;
import com.sparta.spangeats.domain.menu.exception.*;
import com.sparta.spangeats.domain.menu.repository.MenuRepository;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {


    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuService(MenuRepository menuRepository, StoreRepository storeRepository) {
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public MenuResponseDto createMenu(Long storeId, MenuRequestDto requestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));

        if (requestDto.name() == null || requestDto.price() == null) {
            throw new InvalidMenuDataException();
        }

        if (requestDto.description() == null || requestDto.description().isBlank()) {
            throw new InvalidMenuDescriptionException("메뉴 설명은 필수 항목입니다.");
        }

        if (menuRepository.existsByNameAndStoreId(requestDto.name(), storeId)) {
            throw new MenuAlreadyExistsException(requestDto.name());
        }

        Menu menu = new Menu(
                store,
                requestDto.name(),
                requestDto.price(),
                MenuStatus.ACTIVE,
                requestDto.description()
        );

        Menu savedMenu = menuRepository.save(menu);
        return convertToResponseDto(savedMenu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long storeId, Long menuId, MenuRequestDto requestDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("ID " + menuId + "에 해당하는 메뉴를 찾을 수 없습니다."));

        if (!menu.getStore().getId().equals(storeId)) {
            throw new InvalidMenuAccessException("이 메뉴에 대한 접근 권한이 없습니다.");
        }

        menu.setName(requestDto.name());
        menu.setPrice(requestDto.price());
        menu.setDescription(requestDto.description());

        menuRepository.save(menu);
        return convertToResponseDto(menu);
    }


    public void deleteMenu(Long storeId, Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("ID " + menuId + "에 해당하는 메뉴를 찾을 수 없습니다."));

        if (!menu.getStore().getId().equals(storeId)) {
            throw new InvalidMenuAccessException("이 메뉴에 대한 접근 권한이 없습니다.");
        }

        menu.setStatus(MenuStatus.DELETED);
        menuRepository.save(menu);
    }


    public List<MenuResponseDto> getMenusByStoreId(Long storeId) {
        List<Menu> menus = menuRepository.findAllByStoreId(storeId);
        return menus.stream()
                .filter(menu -> !menu.getStatus().equals(MenuStatus.DELETED)) // 삭제된 메뉴 제외
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private MenuResponseDto convertToResponseDto(Menu menu) {
        return new MenuResponseDto(menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getStatus().name(),
                menu.getDescription());
    }
}
