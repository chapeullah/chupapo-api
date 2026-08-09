package org.chapeullah.chupapoapi.project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.chapeullah.chupapoapi.localization.model.Language;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.Set;

public record CreateProjectRequest(

        @NotBlank(message = "Slug must not be blank")
        @Size(
                max = 128,
                message = "Slug must not exceed 128 characters")
        @Pattern(
                regexp = "^[a-z]+(?:-[a-z]+)*$",
                message = "Slug must contain only lowercase English letters separated by single hyphens")
        String slug,

        @NotBlank(message = "Author name must not be blank")
        @Size(
                max = 128,
                message = "Author name must not exceed 128 characters")
        String authorName,

        @NotBlank(message = "Author URL must not be blank")
        @Size(
                max = 8192,
                message = "Author URL must not exceed 8192 characters")
        @URL(message = "Author URL must be valid")
        String authorUrl,

        @NotBlank(message = "Repository URL must not be blank")
        @Size(
                max = 8192,
                message = "Repository URL must not exceed 8192 characters")
        @URL(message = "Repository URL must be valid")
        String repositoryUrl,

        @NotNull(message = "Release date must not be null")
        @PastOrPresent(message = "Release date must be in the past or present")
        LocalDate releaseDate,

        @NotEmpty(message = "Project translations must not be empty")
        Map<
                @NotNull(message = "Translation language must not be null")
                        Language,
                @NotNull(message = "Project translation must not be null")
                @Valid ProjectTranslationRequest> translations,

        @NotEmpty(message = "Project tags must not be empty")
        Set<
                @NotBlank(message = "Project tag must not be blank")
                @Size(
                        max = 32,
                        message = "Project tag must not exceed 32 characters")
                        String> tags,

        @NotEmpty(message = "Project previews must not be empty")
        Set<
                @NotNull(message = "Project preview must not be null")
                @Valid CreateProjectPreviewRequest> previews) {}
