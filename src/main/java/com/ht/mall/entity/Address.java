package com.ht.mall.entity;

import lombok.*;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@ToString
public class Address {

    private String city;

    private String zipcode;

}
