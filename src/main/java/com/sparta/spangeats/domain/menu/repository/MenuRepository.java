package com.sparta.spangeats.domain.menu.repository;

import com.sparta.spangeats.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
