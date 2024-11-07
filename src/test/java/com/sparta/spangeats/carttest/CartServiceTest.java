package com.sparta.spangeats.carttest;

import com.sparta.spangeats.domain.cart.dto.CartSaveRequest;
import com.sparta.spangeats.domain.cart.entity.Cart;
import com.sparta.spangeats.domain.cart.repository.CartRepository;
import com.sparta.spangeats.domain.cart.service.CartService;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.menu.entity.Menu;
import com.sparta.spangeats.domain.menu.entity.MenuStatus;
import com.sparta.spangeats.domain.menu.repository.MenuRepository;
import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  /*  @Test
    void testCreateCartSuccessfully() {
        Member member = new Member("test@example.com", "password123", "nickname", MemberRole.USER, "123-4567");
        Store store = new Store("고기집", LocalTime.of(9, 0), LocalTime.of(21, 0), 15000L, "123-4567", "Some address", "공지", member);
        Menu menu = new Menu("돼지고기", 10000, MenuStatus.ACTIVE, store);
        // given
        CartSaveRequest request = new CartSaveRequest(1L, 1L, 2L, 1000L);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(cartRepository.findByMemberIdAndMenuIdAndStoreId(1L, 1L, 1L)).thenReturn(Optional.empty());

        // when
        cartService.create(request, member);

        // then
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartArgumentCaptor.capture());

        Cart savedCart = cartArgumentCaptor.getValue();
        assertEquals(member, savedCart.getMember());
        assertEquals(menu, savedCart.getMenu());
        assertEquals(store, savedCart.getStore());
        assertEquals(2L, savedCart.getQuantity());
        assertEquals(1000L, savedCart.getPrice());
    }*/
}