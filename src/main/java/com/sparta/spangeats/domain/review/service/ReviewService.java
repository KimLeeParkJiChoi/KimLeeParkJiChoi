package com.sparta.spangeats.domain.review.service;

import com.sparta.spangeats.domain.review.dto.ReviewRequest;
import com.sparta.spangeats.domain.review.dto.ReviewResponse;
import com.sparta.spangeats.domain.review.entity.Review;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 필터 구현 이후 memberId - > member 로 수정해야함
    public String saveReview(Long memberId, Long orderId, @Valid ReviewRequest requestDto) {
        reviewRepository.save(new Review(memberId, orderId, requestDto.score(), requestDto.contents()));
        return "리뷰가 생성되었습니다.";
    }

    // 추가 구현 필요
    public List<ReviewResponse> getALlForStore(Long storeId) {
        return null;
    }

    public Page<ReviewResponse> getAllForMember(int page, int size, String sortBy, boolean isAsc, Long memberId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Review> reviewList = reviewRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId, pageable);

        return reviewList.map(review -> ReviewResponse.create(review));
    }

    @Transactional
    public String update(Long reviewId, ReviewRequest requestDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("리뷰를 찾을 수 없습니다.")
        );

        review.update(requestDto.score(), requestDto.contents());
        return "리뷰를 수정했습니다.";
    }

    public String delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("리뷰를 찾을 수 없습니다.")
        );
        reviewRepository.delete(review);
        return "리뷰가 삭제되었습니다.";
    }
}