package com.sparta.spangeats.domain.review.service;

import com.sparta.spangeats.domain.review.dto.ReviewRequestDto;
import com.sparta.spangeats.domain.review.dto.ReviewResponseDte;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public void saveReview(String username, Long orderId, @Valid ReviewRequestDto requestDto) {

    }

    public List<ReviewResponseDte> getALlForStore(Long storeId) {
        return null;
    }

    public void getAllForMember(Long memberId) {
    }

    public void update(Long reviewId, ReviewRequestDto requestDto) {
    }

    public void delete(Long reviewId) {

    }
}
