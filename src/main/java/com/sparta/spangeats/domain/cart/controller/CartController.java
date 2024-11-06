package com.sparta.spangeats.domain.cart.controller;

import com.sparta.spangeats.domain.cart.dto.*;
import com.sparta.spangeats.domain.cart.service.CartService;
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

    @GetMapping
    public ResponseEntity<CartRetrieveResponse> retrieveCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CartRetrieveResponse response = cartService.retrieve(userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<String> UpdateMenuQuantityInCart(
            @PathVariable Long cartId,
            @Valid @RequestBody CartUpdateMenuRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        cartService.updateMenuQuantity(cartId, request, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteMenuInCart(
            @PathVariable Long cartId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cartService.deleteMenu(cartId, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
 }
