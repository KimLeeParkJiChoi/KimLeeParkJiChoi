package com.sparta.spangeats.domain.store.controller;

import com.sparta.spangeats.common.ErrorResponseDto;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.store.dto.*;
import com.sparta.spangeats.domain.store.exception.StoreException;
import com.sparta.spangeats.domain.store.service.StoreService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (StoreException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("서버 오류가 발생했습니다."));
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
        } catch (StoreException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("서버 오류가 발생했습니다."));
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
    // 4. 가게 전체 조회 (페이징 처리)
    @GetMapping()
    public ResponseEntity<Object> getStores(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<StoreSearchDto> stores = storeService.getStoresByNameAsList(name, page, size);
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("서버 오류가 발생했습니다."));
        }
    }

    // 5. 가게 단건 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<Object> getStoreById(@PathVariable Long storeId) {
        try {
            StoreIdSearchDto responseDto = storeService.getStoreById(storeId);
            return ResponseEntity.ok(responseDto);
        } catch (StoreException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDto(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("서버 오류가 발생했습니다."));
        }
    }
}