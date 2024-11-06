package com.sparta.spangeats.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.spangeats.domain.auth.dto.response.KakaoMemberInfoDto;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.security.config.CustomPasswordEncoder;
import com.sparta.spangeats.security.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final CustomPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public String kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);
        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoMemberInfoDto kakaoMemberInfoDto = getKakaoMemberInfo(accessToken);
        // 3. 필요시에 회원가입
        Member kakaoMember = registerKakaoMemberIfNeeded(kakaoMemberInfoDto);
        // 4. JWT 토큰 반환
        String createToken = jwtUtil.createToken(kakaoMember.getEmail(), kakaoMember.getMemberRole());

        return createToken;
    }

    // 엑세스 토큰 요청
    private String getToken(String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        // Kakao OAuth 토큰 요청을 위한 Content-Type 설정
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // Http Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "216d6970b2f9d144847c1e1b6d6111fe"); // REST API 키
        body.add("redirect_uri", "http://localhost:8080/api/auth/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();

    }

    // 시용자 정보 요청
    private KakaoMemberInfoDto getKakaoMemberInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        /*
           json 으로 온 응답에서 원하는 값만 뽑아 오는 것임
           이 이외의 데이터가 필요하다면 json 형태를 보고 가져올 수 있다.
           */
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoMemberInfoDto(id, nickname, email);
    }

    // 회원가입 처리
    private Member registerKakaoMemberIfNeeded(KakaoMemberInfoDto kakaoMemberInfo) {
        // DB에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoMemberInfo.id();
        Member kakaoMember = memberRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoMember == null) {
            // 카카오 사용자 email 과 동일한 email 을 가진 회원이 있는지 확인
            String kakaoEmail = kakaoMemberInfo.email();
            Member sameEmailMember = memberRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailMember != null) {
                kakaoMember = sameEmailMember;
                // 기존 회원 정보에 카카오 아이디 추가
                kakaoMember.setKakaoId(kakaoId);
            } else {
                // 신규 회원 가입
                /*
                random UUID로 생성한 비밀번호는 카카오 회원가입에 사용되지만
                실제 로그인에 사용되지 않는다.
                 */
                String password = UUID.randomUUID().toString();
                String EncodedPassword = passwordEncoder.encode(password);

                String email = kakaoMemberInfo.email();

                kakaoMember = new Member(
                        kakaoMemberInfo.email(),
                        EncodedPassword,
                        kakaoMemberInfo.nickname(),
                        MemberRole.USER,
                        kakaoMemberInfo.id());
            }

            memberRepository.save(kakaoMember);
        }

        return kakaoMember;
    }

}
