package com.ht.mall;

import com.ht.mall.entity.Address;
import com.ht.mall.entity.Member;
import com.ht.mall.repository.ItemRepository;
import com.ht.mall.repository.MemberRepository;
import com.ht.mall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void testDataInit(){
        Member member = setMember();
        memberRepository.save(member);
    }

    public Member setMember(){
        return Member.builder()
                .name("test name")
                .loginId("test loginId")
                .password("test password")
                .address(new Address("test city","test zipcode"))
                .build();
    }

}
