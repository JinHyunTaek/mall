package com.ht.mall.form.member;

import com.ht.mall.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
@Builder
public class MemberUpdateForm {

    private Long id;

    @NotEmpty
    @Size(min = 2,max = 10)
    private String name;

    @NotEmpty
    @Size(min = 2,max = 10)
    private String loginId;

    @NotEmpty
    @Size(min = 2,max = 10)
    private String password;

    @NotEmpty
    @Size(min = 2,max = 10)
    private String city;

    @NotEmpty
    @Size(min = 5,max = 10)
    private String zipcode;

    public MemberUpdateForm toForm(Member member){
        return MemberUpdateForm.builder()
                .id(member.getId())
                .name(member.getName())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .city(member.getCurrentAddress().getCity())
                .zipcode(member.getCurrentAddress().getZipcode())
                .build();
    }

}
