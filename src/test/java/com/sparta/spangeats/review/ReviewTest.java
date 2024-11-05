package com.sparta.spangeats.review;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.domain.order.repository.OrderRepository;
import com.sparta.spangeats.domain.review.dto.ReviewRequest;
import com.sparta.spangeats.domain.review.entity.Review;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import com.sparta.spangeats.domain.review.service.ReviewService;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class ReviewTest {

    @Mock ReviewRepository reviewRepository;
    @Mock StoreRepository storeRepository;
    @Mock OrderRepository orderRepository;
    @Mock MemberRepository memberRepository;
    @InjectMocks ReviewService reviewService;

    Member member = new Member();
    com.sparta.spangeats.domain.order.entity.Order order = new com.sparta.spangeats.domain.order.entity.Order();
    Review review = new Review();

    @Test
    @DisplayName("리뷰 생성 - 성공")
    @Order(1)
    void testSaveReviewSuccess() {
        // given
        Long memberId = 1L;
        Long orderId = 1L;
        ReviewRequest requestDto = new ReviewRequest(5L, "test");

        member.setId(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        order.setId(orderId);
        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        given(reviewRepository.existsByOrderId(orderId)).willReturn(false);

        Review savedReview = new Review(memberId, orderId, requestDto.score(), requestDto.contents());
        savedReview.setId(1L);
        given(reviewRepository.save(any(Review.class))).willReturn(savedReview);

        // when
        String result = reviewService.saveReview(memberId, orderId, requestDto);

        // then
        assertEquals("리뷰가 생성되었습니다.", result);
        assertEquals("리뷰가 생성되었습니다.", result);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    @DisplayName("가게 별 리뷰 조회 - 성공")
    @Order(2)
    void test2() {

    }

    @Test
    @DisplayName("회원 별 리뷰 조회 - 성공")
    @Order(3)
    void test3() {

    }

    @Test
    @DisplayName("리뷰 수정 - 성공")
    @Order(4)
    void test4() {

    }

    @Test
    @DisplayName("리뷰 삭제 - 성공")
    @Order(5)
    void test5() {

    }
}
