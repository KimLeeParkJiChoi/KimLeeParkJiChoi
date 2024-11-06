package com.sparta.spangeats.review;

import com.sparta.spangeats.domain.review.entity.Review;
import com.sparta.spangeats.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.util.List;

import static org.mockito.Mockito.when;

//@DataJpaTest
//@ActiveProfiles("test") // 테스트용 프로파일을 사용하도록 지정
public class ReviewJpqlTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("가게 별 리뷰 조회 - JPQL 테스트")
    void test() {
        int page = 0;
        int size = 10;
        String sortBy = "updatedAt";
        boolean isAsc = false;

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Review> reviewList = List.of(
                new Review(1L, 1L, 1L, "111"),
                new Review(2L, 2L, 2L, "222"),
                new Review(3L, 3L, 3L, "333"),
                new Review(4L, 4L, 4L, "444")
                );

        Page<Review> reviewPage = new PageImpl<>(reviewList, pageable, reviewList.size());

        when(reviewRepository.findAllForStore(pageable)).thenReturn(reviewPage);

        Page<Review> result = reviewRepository.findAllForStore(pageable);
    }
}