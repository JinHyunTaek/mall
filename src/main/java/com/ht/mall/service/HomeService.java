package com.ht.mall.service;

import com.ht.mall.exeption.BasicException;
import com.ht.mall.projections.member.SimpleMember;
import com.ht.mall.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ht.mall.exeption.ErrorCode.NO_MEMBER_FOUND;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class HomeService {

    private final MemberRepository memberRepository;

    public SimpleMember findById(Long memberId){
        return memberRepository.findSimpleById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
    }

}
