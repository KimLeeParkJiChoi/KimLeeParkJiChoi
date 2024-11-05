package com.sparta.spangeats.domain.order.repository;

import com.sparta.spangeats.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
