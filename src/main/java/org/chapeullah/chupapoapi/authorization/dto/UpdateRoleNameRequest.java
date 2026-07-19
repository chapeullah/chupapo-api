package org.chapeullah.chupapoapi.authorization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateRoleNameRequest(
        @NotBlank(message = "Role name must not be blank")
        @Size(
                min = 5,
                max = 32,
                message = "Role name must be between 5 and 32 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9_]+$",
                message = "Role name may contain only English letters, digits, underscores")
        String name) {}