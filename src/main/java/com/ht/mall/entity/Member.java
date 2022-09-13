package com.ht.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
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

    private String name;

    private String loginId;

    private String password;

    private Integer cash;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private MemberLevel memberLevel;

    public void updateCash(Integer cash){
        this.cash = cash;
    }

}
