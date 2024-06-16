package br.com.guilhermetupi.ecommerce.common.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          WebRequest request) {
        var error = new ApiError(CommonApplicationError.CM_AV_ARGUMENTS_NOT_VALID)
                .setFields(List.of(new ApiErrorField()
                        .setType(exception.getParameterType())
                        .setField(exception.getParameterName())
                        .setMessage(exception.getMessage())));
        var httpStatus = HttpStatus.BAD_REQUEST;
        var apiError = createApiErrorResponse(request, httpStatus, error);
        log.warn("m=handleMissingServletRequestParameter message={} parameterName={} parameterType={}",
                exception.getMessage(), exception.getParameterName(), exception.getParameterType(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status,
                                                                     WebRequest request) {
        var error = new ApiError(CommonApplicationError.CM_UNSUPPORTED_MEDIA_TYPES,
                exception.getContentType(),
                exception.getSupportedMediaTypes().stream().map(MediaType::toString).collect(Collectors.joining(",")));
        var httpStatus = CommonApplicationError.CM_UNSUPPORTED_MEDIA_TYPES.getHttpStatus();
        var apiError = createApiErrorResponse(request, httpStatus, error);
        log.warn("m=handleHttpMediaTypeNotSupported message={} contentType={} supportedMediaTypes={}",
                exception.getMessage(), exception.getContentType(), exception.getSupportedMediaTypes(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = new ApiError(CommonApplicationError.CM_AV_ARGUMENTS_NOT_VALID)
                .setFields(exception.getBindingResult().getFieldErrors().stream()
                        .map(violation -> new ApiErrorField()
                                .setField(violation.getField())
                                .setType(violation.getObjectName())
                                .setCode(violation.getCode())
                                .setMessage(violation.getDefaultMessage()))
                        .toList());
        var httpStatus = HttpStatus.BAD_REQUEST;
        var apiError = createApiErrorResponse(request, httpStatus, error);
        log.warn("m=handleMethodArgumentNotValid message={} fields={}"
                , exception.getMessage(), error.getFields(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        var apiError = createApiErrorResponse(request,
                httpStatus,
                new ApiError(CommonApplicationError.CM_AV_ARGUMENTS_NOT_VALID, exception.getMostSpecificCause().getMessage()));
        log.warn("m=handleHttpMessageNotReadable message={} httpInputMessage={}"
                , exception.getMessage(), exception.getHttpInputMessage(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception, WebRequest request) {
        var error = new ApiError(CommonApplicationError.CM_CV_CONSTRAINT_VIOLATION)
                .setFields(exception.getConstraintViolations().stream()
                        .map(violation -> new ApiErrorField()
                                .setField(violation.getPropertyPath().toString())
                                .setType(violation.getRootBeanClass().getName())
                                .setMessage(violation.getMessage()))
                        .toList());
        var httpStatus = HttpStatus.BAD_REQUEST;
        var apiError = createApiErrorResponse(request, httpStatus, error);
        log.warn("m=handleConstraintViolation message={} fields={}"
                , exception.getMessage(), error.getFields(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<Object> handleMissingParameterException(MissingParameterException exception, WebRequest request) {
        var error = new ApiError()
                .setCode(CommonApplicationError.CM_CV_CONSTRAINT_VIOLATION.getCode())
                .setMessage(exception.getMessage())
                .setFields(List.of(new ApiErrorField()
                        .setField(exception.getParameterName())
                        .setType(exception.getParameterType())
                        .setMessage("não deve ser nulo")));
        var httpStatus = HttpStatus.BAD_REQUEST;
        var apiError = createApiErrorResponse(request, httpStatus, error);
        log.warn("m=handleMissingParameterException message={} fields={}"
                , exception.getMessage(), error.getFields(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var apiError = createApiErrorResponse(request, httpStatus, new ApiError(CommonApplicationError.CM_DATA_NOT_FOUND, exception.getMessage()));
        log.warn("m=handleNotFoundException message={}", exception.getMessage(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException exception, WebRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var apiError = createApiErrorResponse(request, httpStatus, new ApiError(CommonApplicationError.CM_INVALID_PARAMETER, exception.getMessage()));
        log.warn("m=handleInvalidParameterException message={}", exception.getMessage(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException exception, WebRequest request) {
        var error = (BaseApplicationError) exception.getError();
        var httpStatus = error.getHttpStatus();
        var apiError = new ApiError(error, exception.getParameters()).setData(exception.getData());
        if (error == CommonApplicationError.CM_INVALID_PARAMETER
            && exception.getData() instanceof ApiErrorField apiErrorField) {
            apiError.setData(null);
            apiError.setFields(List.of(apiErrorField));
        }

        var apiErrorResponse = createApiErrorResponse(request, httpStatus, apiError);
        if (httpStatus.is4xxClientError()) {
            log.warn("m=handleServiceException message={} error={} parameters={}",
                    exception.getMessage(), error, exception.getParameters());
        } else {
            log.error("m=handleServiceException message={} error={} parameters={}",
                    exception.getMessage(), error, exception.getParameters(), exception);
        }
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<Object> handleIntegrationException(IntegrationException exception, WebRequest request) {
        if (!Objects.isNull(exception.getApplicationError())) {
            var error = exception.getApplicationError();
            var apiErrorResponse = createApiErrorResponse(request, error.getHttpStatus(), new ApiError(exception.getApplicationError(), exception.getParameters()));
            return new ResponseEntity<>(apiErrorResponse, error.getHttpStatus());
        }
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("m=handleIntegrationException message={} error={} parameters={}",
                exception.getMessage(), exception.getError(), exception.getParameters(), exception);
        return new ResponseEntity<>(createApiErrorResponse(request, httpStatus, new ApiError(CommonApplicationError.CM_INTEGRATION_ERROR, exception.getMessage())),
                httpStatus);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGeneralException(RuntimeException exception, WebRequest request) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var apiError = createApiErrorResponse(request, httpStatus, new ApiError(CommonApplicationError.CM_GENERIC_ERROR, exception.getMessage()));
        log.error("m=handleGeneralException message={}", exception.getMessage(), exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    private ApiErrorResponse createApiErrorResponse(WebRequest request, HttpStatus httpStatus, ApiError apiError) {
        return new ApiErrorResponse()
                .setStatusCode(httpStatus.value())
                .setStatusError(httpStatus.getReasonPhrase())
                .setPath(((ServletWebRequest) request).getRequest().getRequestURI())
                .setError(apiError);
    }
}
