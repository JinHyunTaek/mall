package com.ht.mall.dto;

import com.ht.mall.entity.enumType.DeliveryStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderItemSimpleDto {

    private Long itemId;

    private String itemName;

    private String representImageName;

    private Integer itemPrice;

    private Long orderItemId;

    private Integer quantity;

    private Integer totalOrderPrice;

    private LocalDateTime orderDate;

    private DeliveryStatus deliveryStatus;

    @QueryProjection
    public OrderItemSimpleDto(Long itemId, String itemName, String representImageName,
                              Integer itemPrice, Long orderItemId, Integer quantity,
                              Integer totalOrderPrice, LocalDateTime orderDate, DeliveryStatus deliveryStatus) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.representImageName = representImageName;
        this.itemPrice = itemPrice;
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.totalOrderPrice = totalOrderPrice;
        this.orderDate = orderDate;
        this.deliveryStatus = deliveryStatus;
    }

}
