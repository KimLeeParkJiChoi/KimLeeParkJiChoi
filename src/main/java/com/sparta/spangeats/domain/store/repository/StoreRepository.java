package com.sparta.spangeats.domain.store.repository;

import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByName(String name);

    long countByMemberId(Long memberId, StoreStatus status);
}