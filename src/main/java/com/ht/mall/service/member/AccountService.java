package com.ht.mall.service.member;

import com.ht.mall.entity.AddressEntity;
import com.ht.mall.entity.Member;
import com.ht.mall.form.member.account.SaveMemberForm;
import com.ht.mall.projections.member.SimpleMember;
import com.ht.mall.repository.member.AddressRepository;
import com.ht.mall.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void save(SaveMemberForm form){
        Member member = form.toEntity();
        AddressEntity address = form.toAddress(member);
        memberRepository.save(member);
        addressRepository.save(address);
    }

    public Optional<SimpleMember> findByLoginIdAndPassword(String loginId, String password){
        return memberRepository.findByLoginIdAndPassword(loginId, password);
    }

}
