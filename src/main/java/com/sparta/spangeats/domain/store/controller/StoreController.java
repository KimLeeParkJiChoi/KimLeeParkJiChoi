package com.sparta.spangeats.domain.store.controller;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.store.dto.ErrorResponseDto;
import com.sparta.spangeats.domain.store.dto.StoreRequestDto;
import com.sparta.spangeats.domain.store.dto.StoreResponseDto;
import com.sparta.spangeats.domain.store.dto.SuccessResponseDto;
import com.sparta.spangeats.domain.store.service.StoreService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")

public class StoreController {

    private final StoreService storeService;

    //1. 가게 생성
    @PostMapping()
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
    //2. 가게 수정
    @PatchMapping("/{storeId}")
    public ResponseEntity<Object> updateStore(@PathVariable Long storeId,
                                              @Valid @RequestBody StoreRequestDto storeRequestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Member member = userDetails.getMember();
            StoreResponseDto responseDto = storeService.updateStore(storeId, storeRequestDto, member);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("서버 오류가 발생했습니다."));
        }
    }

    // 3. 가게 삭제 (softdelete)
    @PatchMapping("/{storeId}/status")
    public ResponseEntity<Object> softDeleteStore(@PathVariable Long storeId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Member member = userDetails.getMember();
            storeService.softDeleteStore(storeId, member);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SuccessResponseDto("성공적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("서버 오류가 발생했습니다."));
        }
    }

}
