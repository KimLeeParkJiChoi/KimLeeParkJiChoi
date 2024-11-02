package com.sparta.spangeats.domain.review.controller;

import com.sparta.spangeats.domain.review.dto.ReviewRequestDto;
import com.sparta.spangeats.domain.review.dto.ReviewResponseDte;
import com.sparta.spangeats.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// 유저(1:n)와 오더(1:1)랑 연관 관계 있음. -> username, orderId 부분 수정 필요
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성 , 리뷰 조회(가게) 리턴값으로 리턴
    @PostMapping("/save/param")
    public ResponseEntity<List<ReviewResponseDte>> saveComment(@RequestParam String username, Long orderID,
                                                               @Valid @RequestBody ReviewRequestDto requestDto) {
        reviewService.saveReview(username, orderID, requestDto);
        return null;
    }

    // 리뷰 조회(가게)
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewResponseDte>> getComments(@PathVariable Long storeId) {
        return ResponseEntity.ok(reviewService.getALlForStore(storeId));
    }

    //리뷰 전체 조회(회원별) - 날짜순, 디폴트: 최신
    @GetMapping("/get/member/{memberId}")
    public ResponseEntity<List<ReviewResponseDte>> getAllForMember(@PathVariable Long memberId) {
//        Long memberId = request.getAttribute(user).getUserId;
        reviewService.getAllForMember(memberId);
        return null;
    }

    //리뷰 수정. 리뷰 전체 조회(회원별) 리턴값으로 리턴
    @PatchMapping("/update/{reviewId}")
    public ResponseEntity<List<ReviewResponseDte>> update(@PathVariable Long reviewId,
                                                    @RequestBody ReviewRequestDto requestDto) {
        reviewService.update(reviewId, requestDto);
        return null;
    }

    // 리뷰 단건 삭제. 리뷰 전체 조회(회원별) 리턴값으로 리턴
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<List<ReviewResponseDte>> delete(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
        return null;
    }
}
