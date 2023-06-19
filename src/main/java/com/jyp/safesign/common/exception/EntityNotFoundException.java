package com.jyp.safesign.common.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}