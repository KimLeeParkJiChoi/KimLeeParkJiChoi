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
        String accessToken = getToken(code);
        KakaoMemberInfoDto kakaoMemberInfoDto = getKakaoMemberInfo(accessToken);
        Member kakaoMember = registerKakaoMemberIfNeeded(kakaoMemberInfoDto);
        String createToken = jwtUtil.createToken(kakaoMember.getEmail(), kakaoMember.getMemberRole());

        return createToken;
    }

    private String getToken(String code) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "216d6970b2f9d144847c1e1b6d6111fe"); // REST API 키
        body.add("redirect_uri", "http://localhost:8080/api/auth/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();

    }

    private KakaoMemberInfoDto getKakaoMemberInfo(String accessToken) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );


        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoMemberInfoDto(id, nickname, email);
    }

    private Member registerKakaoMemberIfNeeded(KakaoMemberInfoDto kakaoMemberInfo) {
        Long kakaoId = kakaoMemberInfo.id();
        Member kakaoMember = memberRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoMember == null) {
            String kakaoEmail = kakaoMemberInfo.email();
            Member sameEmailMember = memberRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailMember != null) {
                kakaoMember = sameEmailMember;
                kakaoMember.setKakaoId(kakaoId);
            } else {
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
