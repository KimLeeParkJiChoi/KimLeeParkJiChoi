package com.sparta.spangeats.domain.store.controller;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.store.dto.ErrorResponseDto;
import com.sparta.spangeats.domain.store.dto.StoreRequestDto;
import com.sparta.spangeats.domain.store.dto.StoreResponseDto;
import com.sparta.spangeats.domain.store.service.StoreService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")

public class StoreController {

    @Autowired
    private StoreService storeService;

    //1. 가게 생성
    @PostMapping("/")
    public ResponseEntity<Object> createStore(@Valid @RequestBody StoreRequestDto storeRequestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Member member = userDetails.getMember();
            StoreResponseDto responseDto = storeService.createStore(storeRequestDto, member);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(responseDto);
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("서버 오류가 발생했습니다."));
        }
    }
}
