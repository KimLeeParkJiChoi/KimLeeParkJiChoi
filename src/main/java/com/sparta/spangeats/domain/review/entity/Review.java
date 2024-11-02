package com.sparta.spangeats.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long star;
    private String contents;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "todo_id", nullable = false)
    private Long orderId;

    public Review(String contents, Long userId, Long orderId) {
        this.contents = contents;
        this.userId = userId;
        this.orderId = orderId;
    }

    public void update(Long star, String contents) {
        this.star = star;
        this.contents = contents;
    }
}
