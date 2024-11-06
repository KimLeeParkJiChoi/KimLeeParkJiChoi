package com.sparta.spangeats.domain.review.dto;

import com.sparta.spangeats.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long score, String contents, LocalDateTime createdAt, LocalDateTime updatedAt){
    public static ReviewResponse create(Review review) {
        return new ReviewResponse(review.getScore(), review.getContents(),
                review.getCreatedAt(), review.getUpdatedAt());
    }

    public String getContents() {
        return this.contents;
    }
}