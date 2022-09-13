package com.ht.mall.form.member;

import com.ht.mall.entity.Member;
import com.ht.mall.entity.MemberLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class SimpleProfileForm {

    private Long id;

    private String name;

    private MemberLevel memberLevel;

    public SimpleProfileForm toForm(Member member){
        return SimpleProfileForm.builder()
                .id(member.getId())
                .name(member.getName())
                .memberLevel(member.getMemberLevel())
                .build();
    }

}
