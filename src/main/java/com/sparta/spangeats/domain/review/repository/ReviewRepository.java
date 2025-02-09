package com.sparta.spangeats.domain.review.repository;

import com.sparta.spangeats.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    boolean existsByOrderId(Long orderId);

    // 통합 테스트
    @Query(value = "SELECT new Review(s.member.id, o.store.id, r.score, r.contents)"
            + " from Store s"
            + " inner join Order o on s.id = o.store.id"
            + " inner join Review r on o.reviewId = r.id"
            + " order by r.updatedAt")
    Page<Review> findAllForStore(Pageable pageable);
}
