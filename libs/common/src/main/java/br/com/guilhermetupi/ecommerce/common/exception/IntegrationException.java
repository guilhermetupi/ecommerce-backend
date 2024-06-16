package br.com.guilhermetupi.ecommerce.common.exception;

import br.com.guilhermetupi.ecommerce.common.utils.Strings;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static java.util.Objects.nonNull;

@Getter
public class IntegrationException extends BaseException {

    private static final String UNKNOWN_CODE = "UnknownCode";

    private final IntegrationService service;

    private final String code;

    private final String description;

    private final Object[] parameters;

    private final HttpStatus statusCode;

    private final BaseError error;

    private final BaseApplicationError applicationError;

    public IntegrationException(IntegrationService service, BaseApplicationError error, Object... messageParameters) {
        this(service, error.getCode(), error.getDescription(), null, error, null, error, messageParameters);
    }

    public IntegrationException(IntegrationService service, BaseError error, Object... messageParameters) {
        this(service, error.getCode(), error.getDescription(), null, error, null, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, BaseError error, Throwable cause, Object... messageParameters) {
        this(service, error.getCode(), error.getDescription(), null, error, cause, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, BaseError error, String description, Object... messageParameters) {
        this(service, error.getCode(), description, null, error, null, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, BaseError error, String description, Throwable cause, Object... messageParameters) {
        this(service, error.getCode(), description, null, error, cause, null, messageParameters);
    }

    public IntegrationException(String description, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, UNKNOWN_CODE, description, null, null, null, null, messageParameters);
    }

    public IntegrationException(String description, Throwable cause, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, UNKNOWN_CODE, description, null, null, cause, null, messageParameters);
    }

    public IntegrationException(String code, String description, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, code, description, null, null, null, null, messageParameters);
    }

    public IntegrationException(String code, String description, Throwable cause, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, code, description, null, null, cause, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, String code, String description, Object... messageParameters) {
        this(service, code, description, null, null, null, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, String code, String description, Throwable cause, Object... messageParameters) {
        this(service, code, description, null, null, cause, null, messageParameters);
    }

    public IntegrationException(String description, HttpStatus statusCode, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, UNKNOWN_CODE, description, statusCode, null, null, null, messageParameters);
    }

    public IntegrationException(String description, HttpStatus statusCode, Throwable cause, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, UNKNOWN_CODE, description, statusCode, null, cause, null, messageParameters);
    }

    public IntegrationException(String code, String description, HttpStatus statusCode, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, code, description, statusCode, null, null, null, messageParameters);
    }

    public IntegrationException(String code, String description, HttpStatus statusCode, Throwable cause, Object... messageParameters) {
        this(IntegrationService.UNKNOWN, code, description, statusCode, null, cause, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, String code, String description, HttpStatus statusCode, Object... messageParameters) {
        this(service, code, description, statusCode, null, null, null,  messageParameters);
    }

    public IntegrationException(IntegrationService service, String code, String description, HttpStatus statusCode, Throwable cause, Object... messageParameters) {
        this(service, code, description, statusCode, null, cause, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, String code, String description, HttpStatus statusCode, BaseError error, Object... messageParameters) {
        this(service, code, description, statusCode, error, null, null, messageParameters);
    }

    public IntegrationException(IntegrationService service, String code, String description, HttpStatus statusCode, BaseError error, Throwable cause, BaseApplicationError applicationError, Object... messageParameters) {
        super(Strings.formatMessage("[{}] {} - {}", service.getName(), code, Strings.formatMessage(description, messageParameters)), error, nonNull(error) ? error.getCode() : (nonNull(applicationError) ? applicationError.getCode() : code), cause);
        this.service = service;
        this.code = code;
        this.description = Strings.formatMessage(description, messageParameters);
        this.parameters = messageParameters;
        this.statusCode = statusCode;
        this.error = error;
        this.applicationError = applicationError;
    }
}