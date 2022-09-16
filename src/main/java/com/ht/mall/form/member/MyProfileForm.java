package com.ht.mall.form.member;

import com.ht.mall.entity.Member;
import com.ht.mall.entity.enumType.MemberLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyProfileForm {

    private Long id;

    private String name;

    private MemberLevel memberLevel;

    private Integer cash;

    public MyProfileForm toForm(Member member){
        return MyProfileForm.builder()
                .id(member.getId())
                .name(member.getName())
                .memberLevel(member.getMemberLevel())
                .cash(member.getCash())
                .build();
    }

}
