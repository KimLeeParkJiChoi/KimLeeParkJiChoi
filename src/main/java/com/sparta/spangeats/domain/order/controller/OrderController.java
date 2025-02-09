package com.sparta.spangeats.domain.order.controller;

import com.sparta.spangeats.domain.order.dto.OrderResponse;
import com.sparta.spangeats.domain.order.dto.OrderSaveRequest;
import com.sparta.spangeats.domain.order.dto.OrderSaveResponse;
import com.sparta.spangeats.domain.order.dto.OrderUpdateStatusRequest;
import com.sparta.spangeats.domain.order.service.OrderService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> retrieveOneOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        OrderResponse response = orderService.retrieveOne(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<OrderResponse>> retrieveOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<OrderResponse> responses = orderService.retrieveAll(userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<String> updateStatusOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderUpdateStatusRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateOrderStatus(orderId, request, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

/*    @GetMapping("/{storeId}/{orderId}")
    public ResponseEntity<OrderResponse> retrieveOneOrderByOwner(
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        OrderResponse response = orderService.retrieveOneByOwner(storeId, orderId, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }*/
}
