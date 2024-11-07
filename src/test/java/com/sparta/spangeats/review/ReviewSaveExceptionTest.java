package com.sparta.spangeats.review;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.domain.order.repository.OrderRepository;
import com.sparta.spangeats.domain.review.dto.ReviewRequest;
import com.sparta.spangeats.domain.review.entity.Review;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import com.sparta.spangeats.domain.review.service.ReviewService;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewSaveExceptionTest {

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    StoreRepository storeRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    ReviewService reviewService;

    Member member = new Member();
    com.sparta.spangeats.domain.order.entity.Order order = new com.sparta.spangeats.domain.order.entity.Order();
    Review review = new Review();

    @Test
    @DisplayName("맴버를 찾을 수 없는 경우")
    void test1() {
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
        review = savedReview;

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.saveReview(memberId, orderId, requestDto);}
        );

        // then
        assertEquals(
                "회원 정보를 찾을 수 없습니다. 다시 로그인 해주세요.",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("해당 주문을 찾을 수 없는 경우")
    void test2() {
        // given
        Long memberId = 1L;
        Long orderId = 1L;
        ReviewRequest requestDto = new ReviewRequest(5L, "test");

        member.setId(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.saveReview(memberId, orderId, requestDto);}
        );

        // then
        assertEquals(
                "해당 주문을 찾을 수 없습니다.",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("해당 주문에 이미 리뷰를 작성한 경우")
    void test3() {
        // given
        Long memberId = 1L;
        Long orderId = 1L;
        ReviewRequest requestDto = new ReviewRequest(5L, "test");

        member.setId(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        order.setId(orderId);
        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        when(reviewRepository.existsByOrderId(orderId)).thenThrow(
                new IllegalArgumentException("해당 주문에 대해 이미 리뷰를 남기셨습니다.")
        );

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.saveReview(memberId, orderId, requestDto);}
        );

        // then
        assertEquals(
                "해당 주문에 대해 이미 리뷰를 남기셨습니다.",
                exception.getMessage()
        );
    }
}