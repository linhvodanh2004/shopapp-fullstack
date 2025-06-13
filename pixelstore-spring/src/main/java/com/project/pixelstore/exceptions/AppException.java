package com.project.pixelstore.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private ErrorType errorType;
    public AppException(ErrorType errorType) {
        super(errorType.getLocalizedMessage());
        this.errorType = errorType;
    }
}
