package com.sparta.spangeats.domain.store.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.store.dto.StoreRequestDto;
import com.sparta.spangeats.domain.store.dto.StoreResponseDto;
import com.sparta.spangeats.domain.store.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;


import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

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

    //@OneToMany(mappedBy = "store") // Store와 Menu의 연관관계
    //private List<Menu> menus = new ArrayList<>();


    //@OneToMany(mappedBy = "store") // Store와 Order의 연관관계
    //private List<Order> orders = new ArrayList<>();


    // 멤버변수 생성자
    public Store(String name, LocalTime openTime, LocalTime closeTime, Long minOrderPrice, String phoneNumber, String address) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

   //폐업 상태 메서드
    public void closeStore() {
        this.status = StoreStatus.CLOSED;
    }

    // 가게 생성시, requestDto -> 엔터티
    public static Store from(StoreRequestDto requestDto) {
        Store store = new Store();
        store.initData(requestDto);
        return store;
    }


    public void initData(StoreRequestDto requestDto) {
        this.name = requestDto.name();
        this.openTime = requestDto.openTime();
        this.closeTime = requestDto.closeTime();
        this.minOrderPrice = requestDto.minOrderPrice();
        this.phoneNumber = requestDto.phoneNumber();
        this.address = requestDto.address();
    }

    // 가게정보 수정시, 수정된 requestDto -> 엔터티 (status 기본값 유지)
    public void updateData(StoreRequestDto requestDto) {
        this.name = requestDto.name();
        this.openTime = requestDto.openTime();
        this.closeTime = requestDto.closeTime();
        this.minOrderPrice = requestDto.minOrderPrice();
        this.phoneNumber = requestDto.phoneNumber();
        this.address = requestDto.address();

    }

    // 엔터티 -> responseDto 변환 메서드
    public StoreResponseDto to() {
        return new StoreResponseDto(
                id,
                name,
                openTime,
                closeTime,
                minOrderPrice,
                phoneNumber,
                address,
                status,
                getCreatedAt(),
                getUpdatedAt()
        );
    }



}