package com.sparta.spangeats.domain.store.service;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.store.dto.StoreRequestDto;
import com.sparta.spangeats.domain.store.dto.StoreResponseDto;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.exception.StoreLimitExceededException;
import com.sparta.spangeats.domain.store.exception.AccessDeniedException;
import com.sparta.spangeats.domain.store.exception.StoreNameAlreadyExistsException;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class StoreService {

    private final StoreRepository storeRepository;
    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // 1. 가게 생성 메소드
    @Transactional
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto, Member member) {
        // 사장님 권한 확인
        if (!member.getRole().equals(MemberRole.OWNER)) {
            throw new AccessDeniedException("사장님 권한이 필요합니다.");
        }
        // 사장님이 가진 가게 수 확인
        Long memberId = member.getId();
        long storeCount = storeRepository.countByMemberId(memberId);
        if (storeCount >= 3) {
            throw new StoreLimitExceededException("사장님은 최대 3개의 가게를 오픈할 수 있습니다.");
        }
        // 가게명 중복 확인
        if (storeRepository.existsByName(storeRequestDto.name())) {
            throw new StoreNameAlreadyExistsException("가게명은 중복될 수 없습니다.");
        }
        // Store 객체 생성 (가게 소유자로 현재 회원 설정하여 저장)
        Store store = Store.from(storeRequestDto, member);
        store.setMember(member);
        storeRepository.save(store);
        return store.to();
    }
}
