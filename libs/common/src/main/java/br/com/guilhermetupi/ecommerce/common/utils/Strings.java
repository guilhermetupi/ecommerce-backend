package br.com.guilhermetupi.ecommerce.common.utils;

import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

public class Strings {

    public static final String SPACE = " ";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String DASH = "-";
    public static final String UNDERSCORE = "_";
    public static final String EMPTY = "";
    public static final String NEW_LINE = "\n";
    public static final String LINE_BREAK = "<br>";
    public static final String SLASH = "/";
    public static final String PERCENTAGE = "%";
    public static final String SEMICOLON = ";";
    public static final String PIPE = "|";
    public static final String AT = "@";
    public static final String CARET = "^";
    public static final String DOLLAR = "$";
    public static final String ASTERISK = "*";
    public static final String BACK_SLASH = "\\";
    public static final String SHARP = "#";
    public static final String AND = "&";
    public static final String QUESTION_MARK = "?";
    public static final String EXCLAMATION_MARK = "!";


    public static String formatMessage(String message, Object... parameters) {
        var quantity = org.springframework.util.StringUtils.countOccurrencesOf(message, "{}");
        if (isNull(parameters)) {
            parameters = getList(new Object[quantity], 0, quantity);
        } else if (parameters.length < quantity) {
            parameters = getList(parameters, parameters.length, quantity);
        }
        var response = MessageFormatter.arrayFormat(message, parameters).getMessage();
        return org.springframework.util.StringUtils.hasLength(response) ? response.trim() : response;
    }

    private static Object[] getList(Object[] parameters, Integer init, Integer end) {
        var parametersList = new ArrayList<>(Arrays.asList(parameters));
        IntStream.range(init, end).forEach(n -> parametersList.add(EMPTY));
        return parametersList.toArray();
    }
}
