package br.com.guilhermetupi.ecommerce.common.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message) {
        super(message);
    }
}
