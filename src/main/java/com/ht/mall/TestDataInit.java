package com.ht.mall;

import com.ht.mall.entity.*;
import com.ht.mall.entity.enumType.ItemCategory;
import com.ht.mall.entity.enumType.MemberLevel;
import com.ht.mall.repository.member.AddressRepository;
import com.ht.mall.repository.member.MemberRepository;
import com.ht.mall.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void testDataInit(){
        setCustomer();
        Member authority = setAuthority();
        for (int i = 1; i <= 20; i++) {
            for(ItemCategory itemCategory : List.of(ItemCategory.values())){
                setItem(i, authority,itemCategory);
            }
        }
    }

    private Member setAuthority(){
        Address address = new Address("test city", "test zipcode");
        Member member = Member.builder()
                .cart(Cart.builder().build())
                .name("test name")
                .loginId("123")
                .password("123")
                .currentAddress(address)
                .memberLevel(MemberLevel.AUTHORITY)
                .cash(100000)
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .member(member)
                .address(address)
                .build();
        addressRepository.save(addressEntity);

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    private Member setCustomer(){
        Address address = new Address("cus city", "cus zipcode");
        Member member = Member.builder()
                .cart(Cart.builder().build())
                .name("customer123")
                .loginId("ccc")
                .password("ccc")
                .currentAddress(address)
                .memberLevel(MemberLevel.CUSTOMER)
                .cash(10000)
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .member(member)
                .address(address)
                .build();
        addressRepository.save(addressEntity);

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public void setItem(int i,Member member, ItemCategory itemCategory){
        ItemImage itemImage = ItemImage.builder()
                .originalImageName("test.gif")
                .storedImageName("test.gif")
                .build();

        Item item = Item.builder()
                .itemName(itemCategory.name() + " item" + i)
                .representImage(itemImage)
                .itemCategory(itemCategory)
                .description("this is test description " + i)
                .price(1000 + i)
                .stock(95 + i)
                .member(member)
                .build();

        itemImage.addItem(item);
        itemRepository.save(item);
    }

}
