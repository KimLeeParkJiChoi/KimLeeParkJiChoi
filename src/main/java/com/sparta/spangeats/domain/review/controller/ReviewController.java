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

    @GetMapping("/store/{storeId}")
    public ResponseEntity<Page<ReviewResponse>> getALlForStore(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "modifiedAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
            @PathVariable Long storeId) {
        return  ResponseEntity.ok(reviewService.getALlForStore(page, size, sortBy, isAsc, storeId));
    }

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