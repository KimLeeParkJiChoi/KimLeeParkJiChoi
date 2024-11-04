package com.sparta.spangeats.domain.menu.service;

import com.sparta.spangeats.domain.menu.dto.request.MenuRequestDto;
import com.sparta.spangeats.domain.menu.dto.response.MenuResponseDto;
import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.menu.entity.MenuStatus;
import com.sparta.spangeats.domain.menu.exception.InvalidMenuAccessException;
import com.sparta.spangeats.domain.menu.exception.InvalidMenuDataException;
import com.sparta.spangeats.domain.menu.exception.MenuAlreadyExistsException;
import com.sparta.spangeats.domain.menu.exception.MenuNotFoundException;
import com.sparta.spangeats.domain.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // 메뉴 생성
    public MenuResponseDto createMenu(Long storeId, MenuRequestDto requestDto) {
        if (requestDto.name() == null || requestDto.price() == null) {
            throw new InvalidMenuDataException();
        }

        if (menuRepository.existsByNameAndStoreId(requestDto.name(), storeId)) {
            throw new MenuAlreadyExistsException(requestDto.name());
        }

        Menu menu = new Menu(
                null,
                requestDto.storeId(),
                requestDto.name(),
                requestDto.price(),
                MenuStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Menu savedMenu = menuRepository.save(menu);
        return convertToResponseDto(savedMenu);
    }

    // 메뉴 수정
    public MenuResponseDto updateMenu(Long storeId, Long menuId, MenuRequestDto requestDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);

        if (!menu.storeId().equals(storeId)) {
            throw new InvalidMenuAccessException();
        }

        if (requestDto.name() == null || requestDto.price() == null) {
            throw new InvalidMenuDataException();
        }

        menu = new Menu(
                menu.id(),
                menu.storeId(),
                requestDto.name(),
                requestDto.price(),
                MenuStatus.ACTIVE, // 상태 ACTIVE 설정
                menu.createdAt(),
                LocalDateTime.now()
        );

        Menu updatedMenu = menuRepository.save(menu);
        return convertToResponseDto(updatedMenu);
    }

    // 메뉴 삭제
    public void deleteMenu(Long storeId, Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);

        if (!menu.storeId().equals(storeId)) {
            throw new InvalidMenuAccessException();
        }

        menu = new Menu(
                menu.id(),
                menu.storeId(),
                menu.name(),
                menu.price(),
                MenuStatus.DELETED,
                menu.createdAt(),
                menu.updatedAt()
        );

        menuRepository.save(menu);
    }

    // 메뉴 목록 조회
    public List<MenuResponseDto> getMenusByStoreId(Long storeId) {
        List<Menu> menus = menuRepository.findAllByStoreId(storeId);
        return menus.stream()
                .filter(menu -> !menu.status().equals(MenuStatus.DELETED)) // 삭제된 메뉴 제외
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // DTO 변환
    private MenuResponseDto convertToResponseDto(Menu menu) {
        return new MenuResponseDto(menu.id(), menu.name(), menu.price(), menu.status().name());
    }
}
