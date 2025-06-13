package com.project.pixelstore.exceptions;

import com.project.pixelstore.components.LocalizationUtils;
import com.project.pixelstore.utils.LocalizationUtilsHolder;
import com.project.pixelstore.utils.MessageKeys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ErrorType {
    USER_NOT_FOUND(MessageKeys.USER_NOT_FOUND, HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(MessageKeys.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND)
    ;
    @Getter
    private final String messageKey;
    @Getter
    private final HttpStatus status;
    public String getLocalizedMessage(){
        LocalizationUtils utils = LocalizationUtilsHolder.getLocalizationUtils();
        return utils.getLocalizedMessage(messageKey);
    }
}
