package com.sparta.spangeats.domain.order.repository;

import com.sparta.spangeats.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMemberId(Long id);
}
