package com.sparta.spangeats.domain.review.service;

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

    @Transactional
    public String saveReview(Long memberId, Long orderId, ReviewRequest requestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        Review review = reviewRepository.findByOrderId(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문에 대해 이미 리뷰를 남기셨습니다."));

        Review savedReview = new Review(memberId, orderId, requestDto.score(), requestDto.contents());

        order.setReviewId(savedReview.getId());

        reviewRepository.save(savedReview);
        return "리뷰가 생성되었습니다.";
    }

    // 추가 구현 필요
    public ResponseEntity<Page<ReviewResponse>> getALlForStore(int page, int size, String sortBy, boolean isAsc, Long storeId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));  // 가게 id 만으로 주문을 찾을 수 있음.

        List<Order> orderList = store.getOrders();

        List<ReviewResponse> response = new ArrayList<>(); // order 테이블에 reviewId 를 넣는다.
        for (Order order : orderList) {
            Long reviewId = order.getReviewId();
            Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                    new IllegalArgumentException("리뷰를 찾을 수 없습니다.")
            );

            response.add(ReviewResponse.create(review));
        }

        // List<ReviewResponse>를 Page<ReviewResponse>로 변환
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), response.size());
        Page<ReviewResponse> pageResponse = new PageImpl<>(response.subList(start, end), pageable, response.size());

        return ResponseEntity.ok(pageResponse);
    }

    /*public Page<ReviewResponse> getAllForMember(int page, int size, String sortBy, boolean isAsc, Long memberId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        //Page<Review> reviewList = reviewRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId, pageable);

        return reviewList.map(review -> ReviewResponse.create(review));
    }*/

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