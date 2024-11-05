package com.sparta.spangeats.domain.menu.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.store.entity.Store;
import jakarta.persistence.*;

@Entity
@Table(name = "menus")
public class Menu extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @ManyToOne
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store; // 가게와의 연관관계

    // @OneToMany(mappedBy = "menu")
    //Set<CartItem> cartItems, // 장바구니 아이템과의 연관관계

    //@OneToMany(mappedBy = "menu")
    //Set<MenuOrder> menuOrders // 주문과의 연관관계
}