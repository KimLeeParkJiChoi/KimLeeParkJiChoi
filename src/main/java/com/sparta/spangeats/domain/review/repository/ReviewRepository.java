package com.sparta.spangeats.domain.review.repository;

import com.sparta.spangeats.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
