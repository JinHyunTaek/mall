package com.ht.mall.entity;

import com.ht.mall.entity.enumType.MemberLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = LAZY,cascade = {PERSIST,REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private String name;

    private String loginId;

    private String password;

    private Integer cash;

    @Embedded
    private Address currentAddress;

    @Enumerated(STRING)
    private MemberLevel memberLevel;

    public void updateCash(Integer cash){
        this.cash = cash;
    }

    public void updateCurrentAddress(Address address){
        this.currentAddress = address;
    }

    public void updateNameIdPw(String name, String loginId, String password){
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }

}
