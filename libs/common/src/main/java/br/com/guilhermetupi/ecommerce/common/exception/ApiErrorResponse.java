package br.com.guilhermetupi.ecommerce.common.exception;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ApiErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer statusCode;
    private String statusError;
    private String path;
    private ApiError error;
}
