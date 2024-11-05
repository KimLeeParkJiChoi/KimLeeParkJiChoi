package com.sparta.spangeats.domain.order.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.address.entity.Address;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends Timestamped {
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

    private Long reviewId;

    @Builder
    public Order(Member member, Store store, Address address,
                 String riderRequest, String storeRequest,
                 PaymentType paymentType, DeliveryType deliveryType) {
        this.member = member;
        this.store = store;
        this.address = address;
        this.status = OrderStatus.REQUEST;
        this.riderRequest = riderRequest;
        this.storeRequest = storeRequest;
        this.paymentType = paymentType;
        this.deliveryType = deliveryType;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
