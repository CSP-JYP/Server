package com.jyp.safesign.common.exception;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}

