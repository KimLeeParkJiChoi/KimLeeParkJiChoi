package com.sparta.spangeats.security.filter;

import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.security.config.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getJwtFromHeader(request);

        if (StringUtils.hasText(tokenValue)) {

            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token Error");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            }

            Claims info = jwtUtil.getMemberInfoFromToken(tokenValue);

            // ADMIN 권한이 필요한 경우 권한 검증
            if (requiresAdminRole(request) && !isAdmin(info)) {
                log.error("접근 권한이 없습니다.");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.");
                return;
            }

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "인증 설정 오류");
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    // ADMIN 권한 검증 메서드
    private boolean isAdmin(Claims info) {
        String role = info.get("auth", String.class);
        return MemberRole.ADMIN.name().equals(role);
    }

    // 특정 요청이 ADMIN 권한이 필요한지 확인하는 메서드
    private boolean requiresAdminRole(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/api/admin");
    }


    // 인증 처리
    private void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
