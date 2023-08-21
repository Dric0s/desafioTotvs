package com.backendtotvs.backendtotvs.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvalidDataException extends RuntimeException {

    private final List<ErrorMessage> errorMessages = new ArrayList<>();

    public InvalidDataException(String message, List<String> errorMessages) {
        super(message);
        for (String errorMessage : errorMessages) {
            this.errorMessages.add(new ErrorMessage(errorMessage));
        }
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }


}