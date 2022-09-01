package com.ht.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

}
