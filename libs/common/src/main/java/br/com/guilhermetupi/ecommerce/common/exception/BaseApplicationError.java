package br.com.guilhermetupi.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseApplicationError extends BaseError {
    HttpStatus getHttpStatus();
}
