package com.ht.mall.repository.member;

import com.ht.mall.entity.Member;
import com.ht.mall.projections.member.SimpleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<SimpleMember> findSimpleById(Long memberId);

    Optional<SimpleMember> findByLoginIdAndPassword
            (@Param("loginId") String loginId, @Param("password")String password);

}
