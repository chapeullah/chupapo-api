package org.chapeullah.chupapoapi.iam.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
        @NotBlank(message = "Username must not be blank")
        @Size(
                min = 5,
                max = 32,
                message = "Username must be between 5 and 32 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9_]+$",
                message = "Username may contain only English letters, digits, and underscores")
        String username,

        @NotBlank(message = "Password must not be blank")
        @Size(
                min = 12,
                max = 128,
                message = "Password must be between 12 and 128 characters")
        String password,

        @NotBlank(message = "Role name must not be blank")
        @Size(max = 32, message = "Role name must not exceed 32 characters")
        String roleName) {}