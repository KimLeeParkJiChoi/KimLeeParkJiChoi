package com.sparta.spangeats.domain.review.service;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.domain.order.entity.Order;
import com.sparta.spangeats.domain.order.repository.OrderRepository;
import com.sparta.spangeats.domain.review.dto.ReviewRequest;
import com.sparta.spangeats.domain.review.dto.ReviewResponse;
import com.sparta.spangeats.domain.review.entity.Review;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String saveReview(Long memberId, Long orderId, ReviewRequest requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("회원 정보를 찾을 수 없습니다. 다시 로그인 해주세요."));

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        if (reviewRepository.existsByOrderId(orderId)) {
            throw new IllegalArgumentException("해당 주문에 대해 이미 리뷰를 남기셨습니다.");
        }

        Review savedReview = new Review(memberId, orderId, requestDto.score(), requestDto.contents());

        reviewRepository.save(savedReview);
        order.setReviewId(savedReview.getId());
        return "리뷰가 생성되었습니다.";
    }

    public Page<ReviewResponse> getALlForStore(
            int page, int size, String sortBy, boolean isAsc, Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);


        Page<Review> reviews = reviewRepository.findAllForStore(pageable);
        Page<ReviewResponse> responses = reviews.map(
                review -> new ReviewResponse(review.getScore(), review.getContents(),
                        review.getCreatedAt(), review.getUpdatedAt())
        );


        return responses;
    }

    public Page<ReviewResponse> getAllForMember(int page, int size, String sortBy, boolean isAsc, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("회원 정보를 찾을 수 없습니다. 다시 로그인 해주세요."));

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Review> reviewList = reviewRepository.findAllByMemberIdOrderByCreatedAtDesc(member.getId(), pageable);

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