package com.sparta.spangeats.domain.cart.service;

import com.sparta.spangeats.domain.cart.dto.CartSaveRequest;
import com.sparta.spangeats.domain.cart.dto.CartSaveResponse;
import com.sparta.spangeats.domain.cart.entity.Cart;
import com.sparta.spangeats.domain.cart.repository.CartRepository;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.menu.repository.MenuRepository;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.exception.StoreException;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public List<CartSaveResponse> create(CartSaveRequest request, Member member) {
        Menu menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> new IllegalArgumentException("해당 menu가 존재하지 않습니다." + request.menuId()));
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new StoreException("해당 store가 존재하지 않습니다." + request.storeId()));

        if (isDifferentStoreId(request.storeId(), member.getId())){
            cartRepository.deleteAllByMemberId(member.getId());
        }

        Cart cart = cartRepository.findByMemberIdAndMenuIdAndStoreId(member.getId(), request.menuId(), request.storeId())
                .map(exsistingCart -> {
                    exsistingCart.updateQuantity(request.quantity());
                    return exsistingCart;
                })
                .orElseGet(()-> Cart.builder()
                        .member(member)
                        .menu(menu)
                        .store(store)
                        .quantity(request.quantity())
                        .price(request.price())
                        .build());

        cartRepository.save(cart);
        return cartRepository.findAllByMemberId(member.getId()).stream()
                .map(CartSaveResponse::from)
                .toList();
    }

    private boolean isDifferentStoreId(Long storeId, Long memberId) {
        return !cartRepository.existsByStoreIdAndMemberId(storeId, memberId);
    }
}
