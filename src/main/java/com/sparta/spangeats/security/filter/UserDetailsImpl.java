package com.sparta.spangeats.security.filter;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.member.enums.MemberStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private final Member member;

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    public Long getMemberId() {
        return member.getId();
    }

    public String getEmail() {
        return member.getEmail();
    }

    public String getPhoneNumber() {
        return member.getPhoneNumber();
    }

    public MemberRole getMemberRole() {
        return member.getMemberRole();
    }

    public MemberStatus getMemberStatus() {
        return member.getMemberStatus();
    }

    public String getNickname() {
        return member.getNickname();
    }

    public LocalDateTime getCreartedAt() {
        return member.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return member.getUpdatedAt();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        MemberRole memberRole = member.getMemberRole();
        String authority = memberRole.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
