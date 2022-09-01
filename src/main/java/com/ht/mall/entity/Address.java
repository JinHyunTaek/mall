package com.ht.mall.entity;

import lombok.*;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Builder
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Address {

    private String city;

    private String zipcode;

}
