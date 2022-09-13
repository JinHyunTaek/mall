package com.ht.mall.form.member;

import com.ht.mall.entity.Address;
import com.ht.mall.entity.Member;
import com.ht.mall.entity.MemberLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter @Setter
public class SaveMemberForm {

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

    @NotNull
    @Size(min = 5,max = 10)
    private String zipcode;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .loginId(loginId)
                .password(password)
                .address(new Address(city,zipcode))
                .memberLevel(MemberLevel.CUSTOMER)
                .cash(10000)
                .build();
    }

}
