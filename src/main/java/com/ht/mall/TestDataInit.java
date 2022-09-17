package com.ht.mall;

import com.ht.mall.entity.*;
import com.ht.mall.entity.enumType.ItemCategory;
import com.ht.mall.entity.enumType.MemberLevel;
import com.ht.mall.repository.MemberRepository;
import com.ht.mall.repository.item.ItemImageRepository;
import com.ht.mall.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void testDataInit(){
        setCustomer();
        Member authority = setAuthority();
        for (int i = 1; i <= 111; i++) {
            setItem(i, authority);
        }
    }

    private Member setAuthority(){
        Member member = Member.builder()
                .cart(Cart.builder().build())
                .name("test name")
                .loginId("123")
                .password("123")
                .address(new Address("test city", "test zipcode"))
                .memberLevel(MemberLevel.AUTHORITY)
                .cash(100000)
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    private Member setCustomer(){
        Member member = Member.builder()
                .cart(Cart.builder().build())
                .name("customer123")
                .loginId("ccc")
                .password("ccc")
                .address(new Address("cus city", "cus zipcode"))
                .memberLevel(MemberLevel.CUSTOMER)
                .cash(10000)
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public void setItem(int i,Member member){
        ItemImage itemImage = ItemImage.builder()
                .originalImageName("test.gif")
                .storedImageName("test.gif")
                .build();

        Item item = Item.builder()
                .itemName("test-itemName" + i)
                .representImage(itemImage)
                .itemCategory(ItemCategory.CLOTHES)
                .description("this is test description " + i)
                .price(1000 + i)
                .stock(95 + i)
                .member(member)
                .build();

        itemImage.addItem(item);
        itemRepository.save(item);
    }

}
