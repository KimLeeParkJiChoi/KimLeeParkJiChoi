package com.sparta.spangeats.review;

import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.domain.order.repository.OrderRepository;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import com.sparta.spangeats.domain.review.service.ReviewService;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ReviewGetForMemberFailTest {
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

    @Test
    @DisplayName("회원 정보를 찾을 수 없는 경우")
    void test() {
        Long memberId = 1L;
        int page = 0;
        int size = 10;
        String sortBy = "updatedAt";
        boolean isAsc = false;

        Exception exception = assertThrows(NullPointerException.class, () -> {
            reviewService.getAllForMember(page, size, sortBy, isAsc, memberId);}
        );

        assertEquals(
                "회원 정보를 찾을 수 없습니다. 다시 로그인 해주세요.",
                exception.getMessage()
        );
    }
}
