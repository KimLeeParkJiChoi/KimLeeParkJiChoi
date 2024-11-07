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
public class ReviewGetForStoreFailTest {

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
    @DisplayName("가게를 찾을 수 없는 경우")
    void test1() {
        Long storeId = 1L;

        int page = 0;
        int size = 10;
        String sortBy = "updatedAt";
        boolean isAsc = false;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.getALlForStore(page, size, sortBy, isAsc, storeId);}
        );

        assertEquals(
                "해당 가게를 찾을 수 없습니다.",
                exception.getMessage()
        );
    }
}
