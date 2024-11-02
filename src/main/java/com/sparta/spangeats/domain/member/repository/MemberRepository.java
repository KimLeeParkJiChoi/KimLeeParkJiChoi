package com.sparta.spangeats.domain.member.repository;

import com.sparta.spangeats.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
