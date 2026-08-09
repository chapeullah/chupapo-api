package org.chapeullah.chupapoapi.localization.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@ConfigurationProperties(prefix = "app.localization")
public record LocalizationProperties(
        @NotEmpty(message = "Localization languages must not be empty")
        Set<
                @Pattern(
                        regexp = "^[a-z]{2,3}$",
                        message = "Language must be a valid lowercase language code")
                String> languages) {}