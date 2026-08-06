package org.chapeullah.chupapoapi.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

public record UpdateProjectRequest(

        @Size(
                max = 128,
                message = "Slug must not exceed 128 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9_]+$",
                message = "Slug may contain only English letters, digits, and underscores")
        String slug,

        @Size(
                max = 128,
                message = "Author name must not exceed 128 characters")
        @Pattern(
                regexp = "(?s).*\\S.*",
                message = "Author name must not be blank")
        String authorName,

        @Size(
                max = 8192,
                message = "Author URL must not exceed 8192 characters")
        @Pattern(
                regexp = "(?s).*\\S.*",
                message = "Author URL must not be blank")
        @URL(message = "Author URL must be valid")
        String authorUrl,

        @Size(
                max = 8192,
                message = "Repository URL must not exceed 8192 characters")
        @Pattern(
                regexp = "(?s).*\\S.*",
                message = "Repository URL must not be blank")
        @URL(message = "Repository URL must be valid")
        String repositoryUrl,

        @PastOrPresent(message = "Release date must be in the past or present")
        LocalDate releaseDate,

        @Size(
                min = 1,
                message = "Project tags must not be empty")
        Set<
                @NotBlank(message = "Project tag must not be blank")
                @Size(
                        max = 32,
                        message = "Project tag must not exceed 32 characters")
                        String> tags,

        @Size(
                min = 1,
                message = "Project previews must not be empty")
        Set<@Valid CreateProjectPreviewRequest> previews) {

    // TODO
    @JsonIgnore
    @AssertTrue(message = "At least one project field must be provided")
    public boolean isAnyFieldPresent() {
        return Stream.of(
                        slug,
                        authorName,
                        authorUrl,
                        repositoryUrl,
                        releaseDate,
                        tags,
                        previews)
                .anyMatch(value -> value != null);
    }
}