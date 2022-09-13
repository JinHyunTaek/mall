package com.ht.mall;

import com.ht.mall.repository.MemberRepository;
import com.ht.mall.repository.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SimpleOrder {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void simpleOrderTest(){

    }

}
