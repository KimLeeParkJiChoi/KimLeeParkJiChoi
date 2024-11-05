package com.sparta.spangeats.domain.store.repository;

import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.enums.StoreStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByName(String name);

    long countByMemberIdAndStatus(Long memberId, StoreStatus status);

    Page<Store> findAllByStatus(StoreStatus status, Pageable pageable);
}