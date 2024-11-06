package com.sparta.spangeats.domain.address.dto;

import com.sparta.spangeats.domain.address.entity.Address;
import lombok.Builder;

@Builder
public record AddressResponse(
        String address
) {
    public static AddressResponse from(Address address) {
        return AddressResponse.builder()
                .address(address.getAddress())
                .build();
    }
}
