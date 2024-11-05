package com.sparta.spangeats.domain.menu.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menus")
public class Menu extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @ManyToOne
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store; // 가게와의 연관관계

    @Column(nullable = false)
    private String description;  // 메뉴 설명

    // 생성자
    public Menu(Store store,
                String name,
                Integer price,
                MenuStatus status,
                String description) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.status = status;
        this.description = description;
    }

    public void update(Store store,
                       String name,
                       Integer price,
                       MenuStatus status,
                       String description) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.status = status;
        this.description = description;
    }

    public Menu() {
    }
    // @OneToMany(mappedBy = "menu")
    //Set<CartItem> cartItems, // 장바구니 아이템과의 연관관계

    //@OneToMany(mappedBy = "menu")
    //Set<MenuOrder> menuOrders // 주문과의 연관관계
}