package com.ht.mall.exeption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    UNAUTHORIZED(401),

    NO_MEMBER_FOUND(500),
    NO_ITEM_FOUND(500),
    NO_ORDER_FOUND(500);

    private final Integer status;

}
