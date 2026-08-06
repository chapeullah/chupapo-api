package org.chapeullah.chupapoapi.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.chapeullah.chupapoapi.project.model.Language;
import org.chapeullah.chupapoapi.project.model.Theme;
import org.hibernate.validator.constraints.URL;

public record CreateProjectPreviewRequest(
        @NotNull(message = "Language must not be null")
        Language language,

        @NotNull(message = "Theme must not be null")
        Theme theme,

        @NotBlank(message = "Image URL must not be blank")
        @Size(
                max = 8192,
                message = "Image URL must not exceed 8192 characters")
        @URL(message = "Image URL must be valid")
        String imageUrl) {}