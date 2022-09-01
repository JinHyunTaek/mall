package com.ht.mall.exeption;

public class BasicException extends RuntimeException{
    public BasicException(String message) {
        super(message);
    }

    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicException(Throwable cause) {
        super(cause);
    }

    protected BasicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
