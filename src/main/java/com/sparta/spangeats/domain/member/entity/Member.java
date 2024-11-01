package com.sparta.spangeats.domain.member.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    private String password;
    @Column
    private String nickname;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    @Column
    private String phoneNumber;

    public Member(String email, String password, String nickname, MemberRole role, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
}
