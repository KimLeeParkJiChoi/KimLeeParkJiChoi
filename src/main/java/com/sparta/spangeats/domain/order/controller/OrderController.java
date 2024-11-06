package com.sparta.spangeats.domain.order.controller;

import com.sparta.spangeats.domain.order.dto.OrderSaveRequest;
import com.sparta.spangeats.domain.order.dto.OrderSaveResponse;
import com.sparta.spangeats.domain.order.service.OrderService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderSaveResponse> createOrder(
            @Valid @RequestBody OrderSaveRequest orderSaveRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        OrderSaveResponse response = orderService.create(orderSaveRequest, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
