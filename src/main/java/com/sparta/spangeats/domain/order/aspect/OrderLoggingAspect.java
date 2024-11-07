package com.sparta.spangeats.domain.order.aspect;

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

    @AfterReturning(value = "execution(* com.sparta.spangeats.domain.order.service.OrderService.create(..))", returning = "response")
    public void logOrderCreation(JoinPoint joinPoint, Object response) {
        Object[] args = joinPoint.getArgs();
        Long storeId = (Long) args[0];
        log.info("새로운 주문 생성 시간: {}, Store ID: {}, Response: {}", LocalDateTime.now(), storeId, response);
    }

    @AfterReturning(value = "execution(* com.sparta.spangeats.domain.order.service.OrderService.updateOrderStatus(..))", returning = "order")
    public void logOrderStatusChange(JoinPoint joinPoint, Object order) {
        Object[] args = joinPoint.getArgs();
        Long orderId = (Long) args[0];
        log.info("주문 상태 변경 시간: {}, Order ID: {}", LocalDateTime.now(), orderId);
    }
}
