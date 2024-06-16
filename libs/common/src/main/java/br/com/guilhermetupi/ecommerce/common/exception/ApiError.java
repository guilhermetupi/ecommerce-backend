package br.com.guilhermetupi.ecommerce.common.exception;

import br.com.guilhermetupi.ecommerce.common.utils.Strings;
import java.util.List;

public class ApiError {
    private static final long serialVersionUID = 3673305337110772582L;

    private String code;

    private String message;

    private List<ApiErrorField> fields;

    private Object data;

    public ApiError() {}

    public ApiError(BaseError error, Object... parameters) {
        this.code = error.getCode();
        this.message = Strings.formatMessage(error.getDescription(), parameters);
    }

    public ApiError(String code, String message, Object... parameters) {
        this.code = code;
        this.message = Strings.formatMessage(message, parameters);
    }

    public String getCode() { return code; }

    public ApiError setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() { return message; }

    public ApiError setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<ApiErrorField> getFields() {
        return fields;
    }

    public ApiError setFields(List<ApiErrorField> fields) {
        this.fields = fields;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ApiError setData(Object data) {
        this.data = data;
        return this;
    }
}
