package com.sparta.spangeats.domain.review.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "review")
public class Review extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long star;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "todo_id", nullable = false)
    private Long orderId;

    private Long memberId;

    public Review(Long memberId, Long orderId, Long star, String contents) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.star = star;
        this.contents = contents;
    }

    public void update(Long star, String contents) {
        this.star = star;
        this.contents = contents;
    }
}
