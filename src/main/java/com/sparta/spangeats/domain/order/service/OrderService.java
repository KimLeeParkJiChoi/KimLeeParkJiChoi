package com.sparta.spangeats.domain.order.service;

import com.sparta.spangeats.domain.address.entity.Address;
import com.sparta.spangeats.domain.address.repository.AddressRepository;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.menu.exception.MenuNotFoundException;
import com.sparta.spangeats.domain.menu.repository.MenuRepository;
import com.sparta.spangeats.domain.order.dto.*;
import com.sparta.spangeats.domain.order.entity.MenuOrder;
import com.sparta.spangeats.domain.order.entity.Order;
import com.sparta.spangeats.domain.order.exception.OrderLowMinPriceException;
import com.sparta.spangeats.domain.order.exception.OrderNotFoundException;
import com.sparta.spangeats.domain.order.exception.OrderNotOpenTimeException;
import com.sparta.spangeats.domain.order.repository.MenuOrderRepository;
import com.sparta.spangeats.domain.order.repository.OrderRepository;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.exception.StoreException;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;
    private final MenuOrderRepository menuOrderRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public OrderSaveResponse create(OrderSaveRequest request, Member member) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new StoreException("가게 정보가 없습니다."));
        Address address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new IllegalArgumentException("주소 정보가 없습니다."));

        checkMinOrderPrice(request.totalPrice(), store.getMinOrderPrice());
        checkStoreOpenTimeAndCloseTime(store.getOpenTime(), store.getCloseTime());

        Order order = Order.builder()
                .member(member)
                .store(store)
                .address(address)
                .riderRequest(request.riderRequest())
                .storeRequest(request.storeRequest())
                .paymentType(request.paymentType())
                .deliveryType(request.deliveryType())
                .totalPrice(request.totalPrice())
                .build();
        orderRepository.save(order);

        createMenuOrders(request.orders(), order);
        return OrderSaveResponse.from(order);
    }

    private void createMenuOrders(List<MenuOrders> orders, Order order) {
        orders.forEach(items -> {
                Menu menu = menuRepository.findById(items.menuId())
                        .orElseThrow(() -> new MenuNotFoundException("해당 메뉴가 존재하지 않습니다."));
                MenuOrder menuOrder = MenuOrder.builder()
                        .menu(menu)
                        .order(order)
                        .quantity(items.quantity())
                        .build();
                menuOrderRepository.save(menuOrder);
        });
    }

    public OrderResponse retrieveOne(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("해당 주문을 조회할 수 없습니다."));

        return OrderResponse.from(order);
    }

    public List<OrderResponse> retrieveAll(Member member) {
        List<Order> orders = orderRepository.findAllByMemberId(member.getId());
        return orders.stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderUpdateStatusRequest request, Member member) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문 내역을 찾을 수 없습니다."));
        order.updateStatus(request.status());
        orderRepository.save(order);
    }

    private void checkStoreOpenTimeAndCloseTime(LocalTime openTime, LocalTime closeTime) {
        LocalTime now = LocalTime.now();
        if (now.isBefore(openTime) || now.isAfter(closeTime)) {
            throw new OrderNotOpenTimeException("영업중이 아닙니다.");
        }
    }

    private void checkMinOrderPrice(Long totalPrice, Long minOrderPrice) {
        if (totalPrice < minOrderPrice) {
            throw new OrderLowMinPriceException("최소주문금액 이상 구매가 가능합니다.");
        }
    }
}
