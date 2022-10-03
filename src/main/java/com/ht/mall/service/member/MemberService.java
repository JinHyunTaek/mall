package com.ht.mall.service.member;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.OrderItemSimpleDto;
import com.ht.mall.entity.Address;
import com.ht.mall.entity.AddressEntity;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.form.member.MemberUpdateForm;
import com.ht.mall.form.member.MyProfileForm;
import com.ht.mall.entity.Member;
import com.ht.mall.form.member.SimpleProfileForm;
import com.ht.mall.repository.member.AddressRepository;
import com.ht.mall.repository.member.MemberRepository;
import com.ht.mall.repository.orderItem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ht.mall.exeption.ErrorCode.NO_MEMBER_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;

    public SimpleProfileForm findSimpleProfileByMemberId(SimpleProfileForm form, Long memberId){
        Member member = findMember(memberId);
        return form.toForm(member);
    }

    public Page<OrderItemSimpleDto> findOrderItem(Pageable pageable, PageItemCond itemCond){
        return orderItemRepository.simpleOrderItem(pageable, itemCond);
    }

    public MyProfileForm setMyProfile(MyProfileForm form,Long memberId){
        Member member = findMember(memberId);
        return form.toForm(member);
    }

    public MemberUpdateForm setUpdateForm(Long memberId, MemberUpdateForm form) {
        Member member = findMember(memberId);
        return form.toForm(member);
    }

    /**
     * (실행되는 쿼리는 이 순서대로 아님. (id 타입 identity 여서 변경감지가 insert 쿼리보다 늦게 나감.) )
     * 1. member currentAddress 변경
     * 2. 새로운 name, loginId, password 로 변경
     * 3. form 으로부터 받은 새로운 address -> AddressEntity 에 저장
     */
    @Transactional
    public void update(MemberUpdateForm form){
        Member member = findMember(form.getId());

        member.updateCurrentAddress(new Address(form.getCity(),form.getZipcode()));
        member.updateNameIdPw(
                form.getName(), form.getLoginId(), form.getPassword()
        );
        AddressEntity newAddress = AddressEntity.builder()
                .address(new Address(form.getCity(), form.getZipcode()))
                .member(member)
                .build();
        addressRepository.save(newAddress);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
    }

}
