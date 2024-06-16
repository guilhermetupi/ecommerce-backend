package br.com.guilhermetupi.ecommerce.common.exception;

import br.com.guilhermetupi.ecommerce.common.utils.Strings;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

@Getter
public class ServiceException extends BaseException {

    private final Object[] parameters;

    private final Object data;

    public ServiceException(String message, BaseApplicationError error) {
        this(message, null, error, null, (Object[]) null);
    }

    public ServiceException(BaseApplicationError error, Object... parameters) {
        this(null, null, error, null, parameters);
    }

    public ServiceException(BaseApplicationError error, Throwable cause, Object... parameters) {
        this(null, null, error, cause, parameters);
    }

    public ServiceException(Object data, BaseApplicationError error, Object... parameters) {
        this(null, data, error, null, parameters);
    }

    public ServiceException(String message, Object data, BaseApplicationError error, Throwable cause, Object... parameters) {
        super(StringUtils.isNotBlank(message) ? message : Strings.formatMessage(error.getDescription(), parameters), error, error.getCode(), cause);
        this.parameters = parameters;
        this.data = data;
    }
}
