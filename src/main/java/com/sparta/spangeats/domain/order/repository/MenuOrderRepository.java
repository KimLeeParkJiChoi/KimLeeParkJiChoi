package com.sparta.spangeats.domain.order.repository;

import com.sparta.spangeats.domain.order.entity.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOrderRepository extends JpaRepository<MenuOrder, Long> {
}
