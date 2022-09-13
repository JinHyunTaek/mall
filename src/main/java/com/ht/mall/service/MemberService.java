package com.ht.mall.service;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.form.member.MyProfileForm;
import com.ht.mall.form.member.SaveMemberForm;
import com.ht.mall.entity.Member;
import com.ht.mall.form.member.SimpleProfileForm;
import com.ht.mall.projections.member.SimpleMember;
import com.ht.mall.repository.MemberRepository;
import com.ht.mall.repository.orderItem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ht.mall.exeption.ErrorCode.NO_MEMBER_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void save(SaveMemberForm form){
        Member member = form.toEntity();
        System.out.println("loginId = " + member.getLoginId());
        memberRepository.save(member);
    }

    public Optional<SimpleMember> findByLoginIdAndPassword(String loginId, String password){
        return memberRepository.findByLoginIdAndPassword(loginId, password);
    }

    public SimpleProfileForm findSimpleProfileByMemberId(SimpleProfileForm form, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        return form.toForm(member);
    }

    public Page<ItemSimpleDto> findOrderItem(Pageable pageable, PageItemCond itemCond){
        return orderItemRepository.simpleItem(pageable, itemCond);
    }

    public MyProfileForm setMyProfile(MyProfileForm form,Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        return form.toForm(member);
    }

}
