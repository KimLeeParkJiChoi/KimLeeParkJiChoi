package com.sparta.spangeats.domain.store.repository;

import com.sparta.spangeats.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    // 가게명이 존재하는지 확인하는 메소드
    boolean existsByName(String name);

    // 사장님이 소유한 가게의 개수를 반환하는 메소드
    long countByMemberId(Long memberId);
}