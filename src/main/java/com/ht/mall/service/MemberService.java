package com.ht.mall.service;

import com.ht.mall.form.SaveMemberForm;
import com.ht.mall.entity.Member;
import com.ht.mall.projections.SimpleMember;
import com.ht.mall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(SaveMemberForm form){
        Member member = form.toEntity();
        System.out.println("loginId = " + member.getLoginId());
        memberRepository.save(member);
    }

    public Optional<SimpleMember> findByLoginIdAndPassword(String loginId, String password){
        return memberRepository.findByLoginIdAndPassword(loginId, password);
    }

}
