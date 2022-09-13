package com.ht.mall.exeption;

import lombok.Getter;

@Getter
public class BasicException extends RuntimeException{

    private ErrorCode errorCode;

    public BasicException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BasicException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BasicException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BasicException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    protected BasicException(String message, Throwable cause, boolean enableSuppression,
                             boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
