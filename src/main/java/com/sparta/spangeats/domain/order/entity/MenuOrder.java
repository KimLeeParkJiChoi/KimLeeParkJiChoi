package com.sparta.spangeats.domain.order.entity;

import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Long quantity;

    @Builder
    public MenuOrder(Menu menu, Order order, Long quantity) {
        this.menu = menu;
        this.order = order;
        this.quantity = quantity;
    }
}
