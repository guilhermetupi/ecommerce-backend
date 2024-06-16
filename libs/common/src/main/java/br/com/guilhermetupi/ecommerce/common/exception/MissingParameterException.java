package br.com.guilhermetupi.ecommerce.common.exception;

import lombok.Getter;

@Getter
public class MissingParameterException extends RuntimeException {

    private final String parameterName;

    private final String parameterType;

    public MissingParameterException(String parameterName, String parameterType) {
        super("O campo \"" + parameterName + "\" é obrigatório");
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }

}
