package com.sparta.spangeats.domain.member.entity;

import com.sparta.spangeats.common.Timestamped;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.member.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member extends Timestamped {
    @Setter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.ACTIVE;

    private Long kakaoId;

    public Member(String email, String password, String nickname, MemberRole role, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberRole = role;
        this.phoneNumber = phoneNumber;
    }

    // 카카오 회원 생성자
    public Member(String email, String password, String nickname, MemberRole role, Long kakaoId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberRole = role;
        this.kakaoId = kakaoId;
    }

    public MemberRole getRole() {
        return memberRole;
    }

    public boolean isValidMemberRole() {
        return this.memberRole.equals(MemberRole.OWNER);
    }

    public boolean isValidNickName(String updateNickName, String currentNickName) {
        if (updateNickName != null && updateNickName.equals(currentNickName)) {
            return false;
        }
        return true;
    }
}
