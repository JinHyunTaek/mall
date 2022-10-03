package com.ht.mall.service.mall;

import com.ht.mall.entity.*;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import com.ht.mall.form.cart.CartForm;
import com.ht.mall.repository.member.MemberRepository;
import com.ht.mall.repository.cart.CartItemRepository;
import com.ht.mall.repository.cart.CartRepository;
import com.ht.mall.repository.item.ItemRepository;
import com.ht.mall.repository.order.OrderRepository;
import com.ht.mall.repository.orderItem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ht.mall.exeption.ErrorCode.NO_CART_ITEM_FOUND;
import static com.ht.mall.exeption.ErrorCode.NO_MEMBER_FOUND;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class CartService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public CartForm getCartFormByMember(Long memberId){
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        List<CartItem> cartItems = cartItemRepository.findWithImageByCartId(cart.getId());
        List<CartForm> cartFormList = cartItems.stream().map(
                cartItem -> CartForm.toForm(cartItem)
        ).collect(toList());
        return CartForm.builder().cartFormList(cartFormList).build();
    }

    @Transactional
    public void saveItemToCart(Long memberId, Long itemId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        Cart cart = member.getCart();

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BasicException(ErrorCode.NO_ITEM_FOUND));

        CartItem cartItem = CartItem.create(cart, item);
        cartItemRepository.save(cartItem);
    }

    /**
     * 1. 구매자 돈 빠짐
     * 2. 해당 상품 재고 감소
     * 3. 판매자 돈 증가
     * 4. order, orderItem 저장
     */
    @Transactional
    public void saveCartItems(List<CartForm> cartItemsForm,Long memberId,int totalPrice) {
        Member customer = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        customer.updateCash(
                customer.getCash() - totalPrice
        );

        cartItemsForm.forEach(
                cartForm -> {
                    CartItem cartItem = cartItemRepository.findWithSalesmanById(cartForm.getCartItemId())
                            .orElseThrow(() -> new BasicException(NO_CART_ITEM_FOUND));
                    Item item = cartItem.getItem();
                    //2
                    item.updateStock(
                            item.getStock() - cartForm.getQuantity()
                    );
                    //3
                    Member salesman = cartItem.getItem().getMember();
                    salesman.updateCash(
                            salesman.getCash() + cartForm.getPrice()*cartForm.getQuantity()
                    );
                    //4
                    Order order = cartForm.toOrder(customer);
                    orderRepository.save(order);
                    OrderItem orderItem = cartForm.toOrderItem(cartForm, order, item);
                    orderItemRepository.save(orderItem);
                });

    }

    public List<CartForm> findByIdIn(List<String> cartItemIds){
        List<CartItem> cartItems = cartItemRepository.findByIdIn(cartItemIds.stream()
                .map(cartItemId -> Long.parseLong(cartItemId))
                .collect(toList()));
        return cartItems.stream()
                .map(cartItem -> CartForm.toForm(cartItem))
                .collect(toList());
    }

    @Transactional
    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

}
