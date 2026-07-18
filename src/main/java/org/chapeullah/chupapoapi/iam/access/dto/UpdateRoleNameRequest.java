package org.chapeullah.chupapoapi.iam.access.dto;

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
                regexp = "^[a-zA-Z_]+$",
                message = "Role name may contain only English letters and underscores")
        String name) {}