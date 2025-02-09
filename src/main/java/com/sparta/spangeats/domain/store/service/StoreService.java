package com.sparta.spangeats.domain.store.service;

import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.store.dto.StoreIdSearchDto;
import com.sparta.spangeats.domain.store.dto.StoreRequestDto;
import com.sparta.spangeats.domain.store.dto.StoreResponseDto;
import com.sparta.spangeats.domain.store.dto.StoreSearchDto;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.enums.StoreStatus;
import com.sparta.spangeats.domain.store.exception.StoreException;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 금기어 리스트
    private static final List<String> FORBIDDEN_WORDS = Arrays.asList("1조바보", "쭈쭈");

    // 금기어 검사 메서드
    private void checkForForbiddenWords(String notice) {
        if (notice != null) {
            for (String forbiddenWord : FORBIDDEN_WORDS) {
                if (notice.contains(forbiddenWord)) {
                    throw new StoreException("공지사항에 금기어가 포함되어 있습니다.");
                }
            }
        }
    }


    // 1. 가게 생성 메소드
    @Transactional
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto, Member member) {
        // 사장님 권한 확인
        if (!member.isValidMemberRole()) {
            throw new StoreException("사장님 권한이 필요합니다.");
        }
        // 사장님이 가진 가게 수 확인 (open상태인 가게만 포함)
        Long memberId = member.getId();
        long storeCount = storeRepository.countByMemberIdAndStatus(memberId, StoreStatus.OPEN);
        if (storeCount >= 3) {
            throw new StoreException("사장님은 최대 3개의 가게를 오픈할 수 있습니다.");
        }
        // 가게명 중복 확인
        if (storeRepository.existsByName(storeRequestDto.name())) {
            throw new StoreException("가게명은 중복될 수 없습니다.");
        }

        // 공지사항에 금기어 포함 여부 확인
        checkForForbiddenWords(storeRequestDto.notice());

        // Store 객체 생성 및 데이터 초기화
        Store store = new Store(
                storeRequestDto.name(),
                storeRequestDto.openTime(),
                storeRequestDto.closeTime(),
                storeRequestDto.minOrderPrice(),
                storeRequestDto.phoneNumber(),
                storeRequestDto.address(),
                storeRequestDto.notice(), // 공지 추가
                member // Member 객체를 전달
        );

        // 가게 소유자로 현재 회원 설정하여 저장
        store.setMember(member);
        storeRepository.save(store);

        // StoreResponseDto로 변환하여 반환
        return StoreResponseDto.from(store);
    }
    // 2. 가게 수정 메소드
    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto storeRequestDto, Member member) {
        // 사장님 권한 확인
        if (!member.isValidMemberRole()) {
            throw new StoreException("사장님 권한이 필요합니다.");
        }
        // Store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException("해당 가게를 찾을 수 없습니다."));

        // 공지사항에 금기어 포함 여부 확인
        checkForForbiddenWords(storeRequestDto.notice());

        // Store 정보 업데이트 (변경된 엔티티를 StoreResponseDto로 변환하여 반환)
        store.updateData(storeRequestDto);
        storeRepository.save(store);
        return StoreResponseDto.from(store);
    }

    //3. 가게 삭제 메소드
    @Transactional
    public void softDeleteStore(Long storeId, Member member) {
        // 사장님 권한 확인
        if (!member.isValidMemberRole()) {
            throw new StoreException("사장님 권한이 필요합니다.");
        }
        // Store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException("해당 가게를 찾을 수 없습니다."));
        // 가게 상태를 CLOSED로 변경하여 저장
        store.closeStore();
        storeRepository.save(store);
    }
    // 4. 가게 전체 조회 메소드 (페이징처리)
    public List<StoreSearchDto> getStoresByNameAsList(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // OPEN 상태의 가게를 페이징 처리하여 가져옴
        Page<Store> storesPage = storeRepository.findAllByStatus(StoreStatus.OPEN, pageable);

        // 필터링과 매핑을 수행하여 List로 변환
        List<StoreSearchDto> filteredStores = storesPage.stream()
                .filter(store -> store.getName().contains(name))
                .map(StoreSearchDto::from)
                .collect(Collectors.toList());

        return filteredStores;
    }

    //5. 가게 단권 조회 메서드
    public StoreIdSearchDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException("존재하지 않는 가게입니다."));

        // 가게 상태가 CLOSED인 경우 예외 처리
        if (store.getStatus() == StoreStatus.CLOSED) {
            throw new StoreException("존재하지 않는 가게입니다.");
        }
        // 등록된 메뉴가 없는 경우 예외 처리
        if (store.getMenus() == null || store.getMenus().isEmpty()) {
            throw new StoreException("아직 메뉴를 준비중입니다.");
        }
        // StoreIdSearchDto로 변환하여 반환
        return StoreIdSearchDto.from(store);
    }
}
