package com.project.pixelstore.utils;

import com.project.pixelstore.components.LocalizationUtils;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Utility holder class used to encapsulate and retain a static reference
 * to the {@code LocalizationUtils} instance, making it accessible globally
 * within the application.
 *
 * This class is marked as a Spring Component to allow for dependency injection
 * of the {@code LocalizationUtils} instance during application initialization.
 *
 * The primary purpose of this class is to centralize access to localization
 * utilities, which include obtaining localized messages based on the current
 * user's locale.
 */
@Component
public class LocalizationUtilsHolder {
    @Getter
    private static LocalizationUtils localizationUtils;

    public LocalizationUtilsHolder(LocalizationUtils localizationUtils) {
        LocalizationUtilsHolder.localizationUtils = localizationUtils;
    }

}
