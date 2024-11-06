package com.sparta.spangeats.domain.order.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.address.entity.Address;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.store.entity.Store;
import jakarta.persistence.*;

import lombok.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends Timestamped {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String riderRequest;
    private String storeRequest;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    private Integer totalPrice;

    @Setter
    @Getter
    private Long reviewId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuOrder> menuOrders = new ArrayList<>();

    @Builder
    public Order(Member member, Store store, Address address,
                 String riderRequest, String storeRequest,
                 PaymentType paymentType, DeliveryType deliveryType, Integer totalPrice) {
        this.member = member;
        this.store = store;
        this.address = address;
        this.status = OrderStatus.REQUEST;
        this.riderRequest = riderRequest;
        this.storeRequest = storeRequest;
        this.paymentType = paymentType;
        this.deliveryType = deliveryType;
        this.totalPrice = totalPrice;
    }

    public Order(Long reviewId) {
        this.reviewId = reviewId;
    }
}
