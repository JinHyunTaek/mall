package com.ht.mall.exeption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    BAD_REQUEST_4xx(444),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),

    INTERNAL_SERVER_ERROR(500),
    NO_MEMBER_FOUND(500),
    NO_CART_ITEM_FOUND(500),
    NO_CART_FOUND(500),
    NO_ITEM_FOUND(500),
    NO_ORDER_FOUND(500);

    private final Integer status;

}
