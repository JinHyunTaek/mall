package com.ht.mall.form.member;

import com.ht.mall.entity.MemberLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileForm {

    private Long id;

    private String name;

    private MemberLevel memberLevel;


}
