package com.project.pixelstore.components;

import com.project.pixelstore.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LocalizationUtils {
    private final LocaleResolver localeResolver;
    private final MessageSource messageSource;

    public String getLocalizedMessage(String messageKey, Object ... args){ // spread operator
        HttpServletRequest request = WebUtils.getCurrentRequest();
        Locale locale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(messageKey, args, locale);
    }
}
