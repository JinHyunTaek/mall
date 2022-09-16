package com.ht.mall.entity;

import com.ht.mall.entity.enumType.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
public class Delivery extends BaseEntity{

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Delivery(Long id, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.deliveryStatus = deliveryStatus;
    }

}
