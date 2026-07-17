package org.chapeullah.chupapoapi.iam.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RenameAccountRequest(
        @NotBlank
        @Size(
                max = 32,
                min = 5,
                message = "Username must be between 5 and 32 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9_]+$",
                message = "Username may contain only English letters, digits, and underscores")
        String username) {}