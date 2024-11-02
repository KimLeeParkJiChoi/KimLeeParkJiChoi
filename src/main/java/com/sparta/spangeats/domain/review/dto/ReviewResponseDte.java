package com.sparta.spangeats.domain.review.dto;

import com.sparta.spangeats.domain.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponseDte(
        Long star, String contents, LocalDateTime createdAt, LocalDateTime updatedAt){
    public static ReviewResponseDte create(Review review) {
        return new ReviewResponseDte(review.getStar(), review.getContents(), review.getCreatedAt(), review.getUpdatedAt());
    }
}