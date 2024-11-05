package com.sparta.spangeats.domain.cart.controller;

import com.sparta.spangeats.domain.cart.dto.CartSaveRequest;
import com.sparta.spangeats.domain.cart.dto.CartSaveResponse;
import com.sparta.spangeats.domain.cart.service.CartService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<List<CartSaveResponse>> createCart(
            @Valid @RequestBody CartSaveRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CartSaveResponse> responses = cartService.create(request, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
}
