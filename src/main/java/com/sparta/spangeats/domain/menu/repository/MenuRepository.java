package com.sparta.spangeats.domain.menu.repository;

import com.sparta.spangeats.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStoreId(Long storeId);
    boolean existsByNameAndStoreId(String name, Long storeId); // 가게 ID를 추가하여 메뉴 이름 확인
}
