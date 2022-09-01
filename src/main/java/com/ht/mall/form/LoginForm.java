package com.ht.mall.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
public class LoginForm {

    @NotEmpty
    @Size(min = 2,max = 10)
    private String loginId;

    @NotEmpty
    @Size(min = 2,max = 10)
    private String password;

}
