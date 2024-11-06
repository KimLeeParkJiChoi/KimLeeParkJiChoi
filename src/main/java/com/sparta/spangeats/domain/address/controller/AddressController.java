package com.sparta.spangeats.domain.address.controller;

import com.sparta.spangeats.domain.address.dto.AddressResponse;
import com.sparta.spangeats.domain.address.dto.AddressSaveRequest;
import com.sparta.spangeats.domain.address.service.AddressService;
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
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<String> createAddress(
            @Valid @RequestBody AddressSaveRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        addressService.create(request, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> retrieveOneAddress(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<AddressResponse> responses = addressService.retrieve(userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
