package com.sparta.spangeats.domain.order.aspect;

import com.sparta.spangeats.domain.order.dto.OrderSaveRequest;
import com.sparta.spangeats.domain.order.dto.OrderSaveResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class OrderLoggingAspect {

    @AfterReturning(value = "execution(* com.sparta.spangeats.domain.order.service.OrderService.create(..))", returning = "result")
    public void logOrderCreation(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        OrderSaveRequest orderSaveRequest = (OrderSaveRequest) args[0]; // Ensure this is cast correctly

        Long storeId = orderSaveRequest.storeId(); // Access store ID directly
        LocalDateTime requestTime = LocalDateTime.now();
        Long orderId = ((OrderSaveResponse) result).orderId();
        log.info("새로운 주문 생성 시간: {}, Store ID: {}, Order ID: {}", requestTime, storeId, orderId);
    }

    @AfterReturning(value = "execution(* com.sparta.spangeats.domain.order.service.OrderService.updateOrderStatus(..))", returning = "order")
    public void logOrderStatusChange(JoinPoint joinPoint, Object order) {
        Object[] args = joinPoint.getArgs();
        Long orderId = (Long) args[0];
        log.info("주문 상태 변경 시간: {}, Order ID: {}", LocalDateTime.now(), orderId);
    }
}
