package com.ht.mall.entity.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryStatus {

    SHIPPING_YET("배송 시작 전"),
    SHIPPING_PREPARING("배송 준비중"),
    SHIPPING("배송중"),
    SHIPPING_COMPLETED("배송 완료");

    private final String message;

}
