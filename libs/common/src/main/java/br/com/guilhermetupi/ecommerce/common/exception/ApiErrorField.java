package br.com.guilhermetupi.ecommerce.common.exception;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiErrorField {
    private String field;
    private String type;
    private String message;
    private String code;
}
