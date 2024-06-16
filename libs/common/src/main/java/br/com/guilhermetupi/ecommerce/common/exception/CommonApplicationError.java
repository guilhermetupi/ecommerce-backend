package br.com.guilhermetupi.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.*;

public enum CommonApplicationError implements BaseApplicationError {

    CM_CV_CONSTRAINT_VIOLATION		                ("001", BAD_REQUEST, "Dados incorretos"),
    CM_AV_ARGUMENTS_NOT_VALID		                ("002", BAD_REQUEST, "Dados inválidos"),
    CM_INTEGRATION_ERROR    		                ("003", INTERNAL_SERVER_ERROR, "Erro desconhecido na integração. {}"),
    CM_STRATEGY_NOT_IMPLEMENTED                     ("004", INTERNAL_SERVER_ERROR, "Estratégia não implementada"),
    CM_RANGE_MORE_THAN_ONE_UNLIMITED_MAX            ("005", BAD_REQUEST, "Intervalos com mais de um limite máximo não definido"),
    CM_RANGE_WITHOUT_UNLIMITED_MAX                  ("006", BAD_REQUEST, "Intervalos sem um máximo ilimitado"),
    CM_RANGE_OVERLAPPING                            ("007", BAD_REQUEST, "Os intervalos não devem se intrelaçar"),
    CM_UNSUPPORTED_MEDIA_TYPES		                ("008", UNSUPPORTED_MEDIA_TYPE, "{} meedia type não suportado, os suportados são: {}"),
    CM_INTEGRATION_UNAVAILABLE 		                ("009", SERVICE_UNAVAILABLE, "Conexão à integração recusada."),
    CM_AUTH_UNAUTHORIZED                            ("010", UNAUTHORIZED, "Não autenticado"),

    CM_AUTH_FORBIDDEN                               ("011", FORBIDDEN, "Não autorizado"),
    CM_ENGINE_CONSTRAINT_REQUIRED_PRODUCT_QUANTITY  ("012", BAD_REQUEST, "Quantidade mínima de produtos não atingida. Insira ao menos {} produto {}."),
    CM_INVALID_RECAPTCHA_TOKEN                      ("013", BAD_REQUEST, "Token do reCaptcha inválido."),
    CM_INVALID_RECAPTCHA_ACTION                     ("014", SERVICE_UNAVAILABLE, "Ação do recaptcha inválida."),
    CM_RECAPTCHA_LOW_SCORE                          ("015", SERVICE_UNAVAILABLE, "Cadastro indisponível no momento"),
    CM_RECAPTCHA_TOKEN_REQUIRED                     ("016", SERVICE_UNAVAILABLE, "Recaptcha token é obrigatório"),
    CM_INVALID_PARAMETER                            ("017", BAD_REQUEST, "Parâmetro inválido: {}"),
    CM_PARAMETER_NOT_FOUND                          ("018", BAD_REQUEST, "Parâmetro obrigatório não encontrado: {}"),
    CM_DATA_NOT_FOUND                               ("019", NOT_FOUND, "Dado não encontrado: {}"),

    CM_UNKNOWN_ERROR				                ("998", INTERNAL_SERVER_ERROR, "Erro desconhecido."),
    CM_GENERIC_ERROR				                ("999", INTERNAL_SERVER_ERROR, "Erro desconhecido. {}"),
    ;

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;

    CommonApplicationError(String code, HttpStatus httpStatus, String description) {
        this.code = "CM-" + code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static CommonApplicationError getByCode(String code) {
        return Stream.of(values()).filter(v -> v.code.equals(code)).findFirst().orElse(CM_UNKNOWN_ERROR);
    }

}
