package com.ht.mall.entity.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    CANCEL_ENABLED("주문 취소 가능 상태"),
    CANCEL_NOT_ENABLED("주문 취소 불가 상태"),
    COMPLETED("주문 최종 완료 상태");

    private final String message;

}
