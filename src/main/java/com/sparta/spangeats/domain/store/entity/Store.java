package com.sparta.spangeats.domain.store.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.order.entity.Order;
import com.sparta.spangeats.domain.store.dto.StoreRequestDto;
import com.sparta.spangeats.domain.store.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Store extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalTime openTime;

    @Column
    private LocalTime closeTime;

    @Column
    private Long minOrderPrice;

    @Enumerated(EnumType.STRING)
    @Column
    private StoreStatus status = StoreStatus.OPEN; // 기본값 설정

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String notice;

    @OneToMany(mappedBy = "store") // Store와 Menu의 연관관계
    private List<Menu> menus = new ArrayList<>();

     @OneToMany(mappedBy = "store") // Store와 Order의 연관관계
     private List<Order> orders = new ArrayList<>();


    @ManyToOne // Store와 Member의 연관관계
    @JoinColumn(name = "member_id") // 외래키 설정
    private Member member;

    public Store(String name, LocalTime openTime, LocalTime closeTime, Long minOrderPrice, String phoneNumber, String address, String notice, Member member) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.notice = notice;
        this.member = member;
    }

    // Member 설정 메서드
    public void setMember(Member member) {
        this.member = member;
    }


    // 가게정보 수정시, 수정된 requestDto -> 엔터티 (status 기본값 유지)
    public void updateData(StoreRequestDto requestDto) {
        this.name = requestDto.name();
        this.openTime = requestDto.openTime();
        this.closeTime = requestDto.closeTime();
        this.minOrderPrice = requestDto.minOrderPrice();
        this.phoneNumber = requestDto.phoneNumber();
        this.address = requestDto.address();
        this.notice = requestDto.notice(); // 공지 추가
    }

    //폐업 상태 메서드
    public void closeStore() {
        this.status = StoreStatus.CLOSED;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setId(Long storeId) {
        this.id = storeId;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}



