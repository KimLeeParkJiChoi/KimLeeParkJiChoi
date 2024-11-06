package com.sparta.spangeats.domain.cart.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private Long quantity;
    private Long price;

    @Builder
    public Cart(Member member, Menu menu, Store store, Long quantity, Long price) {
        this.member = member;
        this.menu = menu;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
    }

    public void updateQuantity(Long additionalQuantity) {
        this.quantity += additionalQuantity;
    }

    public void updateMenuQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public boolean isValidMember(Long checkedMemberId, Long realMemberId) {
        return checkedMemberId.equals(realMemberId);
    }
}
