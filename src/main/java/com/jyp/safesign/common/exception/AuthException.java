package com.jyp.safesign.common.exception;

public class AuthException extends BusinessException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
