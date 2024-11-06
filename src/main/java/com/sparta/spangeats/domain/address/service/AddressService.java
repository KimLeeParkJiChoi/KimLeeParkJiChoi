package com.sparta.spangeats.domain.address.service;

import com.sparta.spangeats.domain.address.dto.AddressSaveRequest;
import com.sparta.spangeats.domain.address.entity.Address;
import com.sparta.spangeats.domain.address.repository.AddressRepository;
import com.sparta.spangeats.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public void create(AddressSaveRequest request, Member member) {
        Address address = Address.builder()
                .address(request.address())
                .member(member)
                .build();
        addressRepository.save(address);
    }
}
