package com.sparta.spangeats.domain.review.service;

import com.sparta.spangeats.domain.review.dto.ReviewRequestDto;
import com.sparta.spangeats.domain.review.dto.ReviewResponseDte;
import com.sparta.spangeats.domain.review.entity.Review;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 필터 구현 이후 userId - > member 로 수정해야함
    public void saveReview(Long memberId, Long orderId, @Valid ReviewRequestDto requestDto) {
        reviewRepository.save(new Review(memberId, orderId, requestDto.star(), requestDto.contents()));
    }

    public List<ReviewResponseDte> getALlForStore(Long storeId) {
        return null;
    }

    public Page<ReviewResponseDte> getAllForMember(int page, int size, String sortBy, boolean isAsc, Long memberId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Review> reviewList = reviewRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId, pageable);

        return reviewList.map(review -> ReviewResponseDte.create(review));
    }


    public void update(Long reviewId, ReviewRequestDto requestDto) {
    }

    public void delete(Long reviewId) {

    }
}