package com.ht.mall;

import com.ht.mall.entity.*;
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
    private final ItemImageRepository itemImageRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void testDataInit(){
        setCustomer();
        Member authority = setAuthority();
        for (int i = 1; i <= 111; i++) {
            setItem(i, authority);
        }
    }

    public Member setAuthority(){
        Member member = Member.builder()
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

    public Member setCustomer(){
        Member member = Member.builder()
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
        Item item = Item.builder()
                .itemName("test-itemName" + i)
                .itemCategory(ItemCategory.CLOTHES)
                .description("this is test description " + i)
                .price(5000 + i)
                .stock(95 + i)
                .member(member)
                .build();

        Item save = itemRepository.save(item);

        ItemImage itemImage = ItemImage.builder()
                .item(save)
                .originalImageName("test" + i + ".gif")
                .storedImageName("test" + i + ".gif")
                .build();
        ItemImage savedItemImage = itemImageRepository.save(itemImage);
        item.setRepresentImage(savedItemImage);
    }
}
