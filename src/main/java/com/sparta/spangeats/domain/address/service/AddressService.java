package com.sparta.spangeats.domain.address.service;

import com.sparta.spangeats.domain.address.dto.AddressResponse;
import com.sparta.spangeats.domain.address.dto.AddressSaveRequest;
import com.sparta.spangeats.domain.address.entity.Address;
import com.sparta.spangeats.domain.address.exception.AddressOverCountException;
import com.sparta.spangeats.domain.address.repository.AddressRepository;
import com.sparta.spangeats.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public void create(AddressSaveRequest request, Member member) {
        if (addressRepository.countByMemberId(member.getId()) >= 10) {
            throw new AddressOverCountException("주소는 최대 10개까지 만들 수 있습니다.");
        }
        Address address = Address.builder()
                .address(request.address())
                .member(member)
                .build();
        addressRepository.save(address);
    }

    public List<AddressResponse> retrieve(Member member) {
        return addressRepository.findAllByMemberId(member.getId()).stream()
                .map(AddressResponse::from)
                .toList();
    }
}
