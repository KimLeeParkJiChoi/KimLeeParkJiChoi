package com.sparta.spangeats.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.awt.*;

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

    private int quantity;

    @Builder
    public MenuOrder(Menu menu, Order order, int quantity) {
        this.menu = menu;
        this.order = order;
        this.quantity = quantity;
    }
}
