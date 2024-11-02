package com.sparta.spangeats.domain.review.service;

import com.sparta.spangeats.domain.review.dto.ReviewRequestDto;
import com.sparta.spangeats.domain.review.dto.ReviewResponseDte;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {


    public void saveReview(String username, Long orderID, @Valid ReviewRequestDto requestDto) {

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
