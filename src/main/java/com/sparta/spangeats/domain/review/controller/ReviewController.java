package com.sparta.spangeats.domain.review.controller;

import com.sparta.spangeats.domain.review.dto.ReviewRequest;
import com.sparta.spangeats.domain.review.dto.ReviewResponse;
import com.sparta.spangeats.domain.review.service.ReviewService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


// 유저(1:n)와 오더(1:1)랑 연관 관계 있음. -> username, orderId 부분 수정 필요
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/save/param")
    public ResponseEntity<String> saveReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestParam Long orderId,
                                             @Valid @RequestBody ReviewRequest requestDto) {
        Long memberId = userDetails.getMemberId();
        String message = reviewService.saveReview(memberId, orderId, requestDto);
        return ResponseEntity.ok(message);
    }

   //  리뷰 조회(가게) 추가 구현 필요, 가게와 주문의 연관관계 형성 후 가능
    @GetMapping("/store/{storeId}")
    public ResponseEntity<Page<ReviewResponse>> getALlForStore(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "modifiedAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
            @PathVariable Long storeId) {
        return reviewService.getALlForStore(page, size, sortBy, isAsc, storeId);
    }

    //리뷰 전체 조회(회원별) - 날짜순, 디폴트: 최신
    @GetMapping("/get/review/member")
    public ResponseEntity<Page<ReviewResponse>> getAllForMember(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "modifiedAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMemberId();
        Page<ReviewResponse> reviews = reviewService.getAllForMember(page, size, sortBy, isAsc, memberId);
        return ResponseEntity.ok(reviews);
    }

    @PatchMapping("/update/{reviewId}")
    public ResponseEntity<String> update(@PathVariable Long reviewId,
                                         @RequestBody ReviewRequest requestDto) {
        String message = reviewService.update(reviewId, requestDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable Long reviewId) {
        String message = reviewService.delete(reviewId);
        return ResponseEntity.ok(message);
    }
}