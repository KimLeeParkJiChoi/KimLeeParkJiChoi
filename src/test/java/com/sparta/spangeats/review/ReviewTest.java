package com.sparta.spangeats.review;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.domain.order.repository.OrderRepository;
import com.sparta.spangeats.domain.review.dto.ReviewRequest;
import com.sparta.spangeats.domain.review.dto.ReviewResponse;
import com.sparta.spangeats.domain.review.entity.Review;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import com.sparta.spangeats.domain.review.service.ReviewService;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    Store store = new Store();

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
        review = savedReview;

        // when
        String result = reviewService.saveReview(memberId, orderId, requestDto);

        // then
        assertEquals("리뷰가 생성되었습니다.", result);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }
/*  이전 로직에 대한 테스트이므로 주석처리
    @Test
    @DisplayName("가게 별 리뷰 조회 - 성공")
    @Order(2)
    void test2() {
        // given
        Long storeId = 1L;
        store.setId(storeId);
        int page = 0;
        int size = 10;
        String sortBy = "updatedAt";
        boolean isAsc = false;
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);


        List<com.sparta.spangeats.domain.order.entity.Order> orders = List.of(
                new com.sparta.spangeats.domain.order.entity.Order(1L),
                new com.sparta.spangeats.domain.order.entity.Order(2L),
                new com.sparta.spangeats.domain.order.entity.Order(3L)
        );
        store.setOrders(orders);
        given(storeRepository.findById(storeId)).willReturn(Optional.of(store));

        LocalDateTime time = LocalDateTime.now();
        given(reviewRepository.findById(1L)).willReturn(Optional.of(
                new Review(1L, 1L, 5L, "Great!")));
        given(reviewRepository.findById(2L)).willReturn(Optional.of(
                new Review(1L, 2L, 4L, "Good!")));
        given(reviewRepository.findById(3L)).willReturn(Optional.of(
                new Review(1L, 2L, 4L, "Good!")));

        // when
        Page<ReviewResponse> result = reviewService.getALlForStore(page, size, sortBy, isAsc, storeId);

        // then
        assertEquals(3, result.getTotalElements());

        List<ReviewResponse> responses = result.getContent();
        assertEquals("Great!", responses.get(0).getContents());
        assertEquals("Good!", responses.get(1).getContents());
    }*/


    @Test
    @DisplayName("회원 별 리뷰 조회 - 성공")
    @Order(3)
    void test3() {
        // given
        Long memberId = 1L;
        member.setId(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        int page = 0;
        int size = 10;
        String sortBy = "updatedAt";
        boolean isAsc = false;

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 페이징된 가짜 리뷰 데이터 생성
        List<Review> reviews = List.of(
                new Review(memberId, 1L, 5L, "Great!"),
                new Review(memberId, 2L, 4L, "Good!"),
                new Review(memberId, 3L, 3L, "Okay")
        );
        Page<Review> reviewPage = new PageImpl<>(reviews, pageable, reviews.size());
        given(reviewRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId, pageable)).willReturn(reviewPage);

        // when
        Page<ReviewResponse> result = reviewService.getAllForMember(page, size, sortBy, isAsc, memberId);

        // then
        assertEquals(reviews.size(), result.getTotalElements()); // 총 리뷰 수 확인
        assertEquals(3, result.getTotalElements()); // 기대한 리뷰 개수와 같은지 확인

        // 각 리뷰가 올바르게 매핑되었는지 확인
        List<ReviewResponse> responses = result.getContent();
        assertEquals("Great!", responses.get(0).getContents());
        assertEquals("Good!", responses.get(1).getContents());
        assertEquals("Okay", responses.get(2).getContents());

        // 각 리뷰의 멤버 ID가 예상과 일치하는지 확인
        for (Review response : reviewPage) {
            assertEquals(memberId, response.getMemberId());
        }
    }

    @Test
    @DisplayName("리뷰 수정 - 성공")
    @Order(4)
    void test4() {
        // given
        Long id = 1L;
        ReviewRequest request = new ReviewRequest(2L, "test");
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        review.update(2L, "test");
        // when
        String result = reviewService.update(1L, request);

        // then
        assertEquals("리뷰를 수정했습니다.", result);
        assertEquals(2L, review.getScore());
        assertEquals("test", review.getContents());
    }

    @Test
    @DisplayName("리뷰 삭제 - 성공")
    @Order(5)
    void test5() {
        // given
        Long id = 10L;
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        // when
        String result = reviewService.delete(10L);

        // then
        assertEquals("리뷰가 삭제되었습니다.", result);
        verify(reviewRepository, times(1)).delete(any(Review.class));

    }
}
