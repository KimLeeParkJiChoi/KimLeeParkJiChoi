package com.sparta.spangeats.authtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.spangeats.security.config.WebSecurityConfig;
import com.sparta.spangeats.security.filter.JwtAuthenticationFilter;
import com.sparta.spangeats.security.filter.JwtAuthorizationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest // 필터 체인을 검증하기 위한 테스트
@Import(WebSecurityConfig.class) // WebSecurityConfig를 임포트하여 Security 설정 포함
public class FilterChainTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Test
    public void whenRequestWithoutAuthToken_thenUnauthorized() throws Exception {
        // 인증 토큰이 없는 요청을 보냈을 때, 401 Unauthorized 반환을 확인
        mockMvc.perform(MockMvcRequestBuilders.get("/api/protected-endpoint")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenRequestWithValidAuthToken_thenControllerIsCalled() throws Exception {
        // 유효한 인증 토큰을 시뮬레이션하여 필터가 컨트롤러에 요청을 전달하는지 확인
        String token = "validJwtToken"; // 유효한 토큰을 가정

        // jwtAuthenticationFilter가 요청을 인증하도록 모킹
        when(jwtAuthenticationFilter.attemptAuthentication(any(), any()))
                .thenReturn(null); // 필요한 경우 적절한 Authentication 객체 반환

        mockMvc.perform(MockMvcRequestBuilders.get("/api/protected-endpoint")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRequestWithInvalidAuthToken_thenUnauthorized() throws Exception {
        // 잘못된 인증 토큰을 가진 요청에 대해 401 응답을 확인
        String invalidToken = "invalidJwtToken";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/protected-endpoint")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
