package com.harinem.identity_service.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;


}
