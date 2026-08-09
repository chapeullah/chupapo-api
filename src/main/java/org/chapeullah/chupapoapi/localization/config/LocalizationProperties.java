package org.chapeullah.chupapoapi.localization.config;

import jakarta.validation.constraints.NotEmpty;
import org.chapeullah.chupapoapi.localization.model.Language;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@ConfigurationProperties(prefix = "app.localization")
public record LocalizationProperties(
        @NotEmpty(message = "Localization languages must not be empty")
        Set<Language> languages) {}