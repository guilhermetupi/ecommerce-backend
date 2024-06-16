package br.com.guilhermetupi.ecommerce.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    protected final BaseError error;

    protected final String code;

    protected BaseException(String message, BaseError error, String code, Throwable cause) {
        super(message, cause);
        this.error = error;
        this.code = code;
    }
}
