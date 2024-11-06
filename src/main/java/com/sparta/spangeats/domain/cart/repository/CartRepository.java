package com.sparta.spangeats.domain.cart.repository;

import com.sparta.spangeats.domain.cart.entity.Cart;
import com.sparta.spangeats.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteAllByMemberId(Long id);

    Optional<Cart> findByMemberIdAndMenuIdAndStoreId(Long id, Long menuId, Long storeId);

    List<Cart> findAllByMemberId(Long id);

    boolean existsByStoreIdAndMemberId(Long storeId, Long memberId);

    void deleteAllByUpdatedAtBefore(LocalDateTime checkTime);
}
