package com.ht.mall.service.mall;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.Member;
import com.ht.mall.entity.Order;
import com.ht.mall.entity.OrderItem;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.form.order.OrderForm;
import com.ht.mall.repository.orderItem.OrderItemRepository;
import com.ht.mall.repository.item.ItemRepository;
import com.ht.mall.repository.member.MemberRepository;
import com.ht.mall.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ht.mall.exeption.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderForm setItemOrder(Long itemId, Long memberId, OrderForm orderForm){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BasicException(NO_ITEM_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        if(item.getMember() == member){
            throw new BasicException(FORBIDDEN);
        }
        return orderForm.toForm(item,member);
    }

    /**
     * 1. 구매자 돈 빠짐
     * 2. 해당 상품 재고 감소
     * 3. 판매자 돈 증가
     * 4. order, orderItem 저장
     */
    @Transactional
    public void saveOrderItem(OrderForm orderForm,Long memberId){
        Member customer = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        customer.updateCash(
                customer.getCash() - (orderForm.getPrice()*orderForm.getQuantity())
        );
        Item item = itemRepository.findById(orderForm.getItemId())
                .orElseThrow(() -> new BasicException(NO_ITEM_FOUND));

        item.updateStock(
                item.getStock() - orderForm.getQuantity()
        );

        Member salesman = item.getMember();
        salesman.updateCash(
                salesman.getCash() + (orderForm.getPrice()*orderForm.getQuantity())
        );

        Order order = orderForm.toEntity(customer);
        orderRepository.save(order);

        OrderItem orderItem = orderForm.toEntity(orderForm, order,item);
        orderItemRepository.save(orderItem);
    }

    public Integer getCashByMemberId(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        return member.getCash();
    }

}
