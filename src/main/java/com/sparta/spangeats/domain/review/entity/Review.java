package com.sparta.spangeats.domain.review.entity;

import com.sparta.spangeats.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "review")
@Setter
public class Review extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long score;
    private String contents;

    private Long orderId;

    private Long memberId;

    public Review(Long memberId, Long orderId, Long score, String contents) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.score = score;
        this.contents = contents;
    }

    public void update(Long star, String contents) {
        this.score = star;
        this.contents = contents;
    }
}