package com.ht.mall.service;

import com.ht.mall.entity.Cart;
import com.ht.mall.entity.CartItem;
import com.ht.mall.entity.Item;
import com.ht.mall.entity.Member;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import com.ht.mall.form.CartForm;
import com.ht.mall.repository.MemberRepository;
import com.ht.mall.repository.cart.CartItemRepository;
import com.ht.mall.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class CartService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

//    public CartForm getCartByMember(Long memberId){
//
//    }

    @Transactional
    public void saveItemToCart(Long memberId, Long itemId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(ErrorCode.NO_MEMBER_FOUND));
        Cart cart = member.getCart();

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BasicException(ErrorCode.NO_ITEM_FOUND));

        CartItem cartItem = CartItem.create(cart, item);
        cartItemRepository.save(cartItem);
    }

}
