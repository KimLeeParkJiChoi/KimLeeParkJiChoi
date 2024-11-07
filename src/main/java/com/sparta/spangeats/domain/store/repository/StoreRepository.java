package com.sparta.spangeats.domain.store.repository;

import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.enums.StoreStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByName(String name);

    long countByMemberIdAndStatus(Long memberId, StoreStatus status);

    Page<Store> findAllByStatusAndNameContaining(StoreStatus status, String name, Pageable pageable);

    // CLOSED 상태이고, deletedAt이 현재 시각보다 이전인 가게들을 찾는 메서드
    List<Store> findByStatusAndDeletedAtBefore(StoreStatus status, LocalDateTime deletedAt);
}