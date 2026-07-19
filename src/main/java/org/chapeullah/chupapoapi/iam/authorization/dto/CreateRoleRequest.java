package org.chapeullah.chupapoapi.iam.authorization.dto;

import jakarta.validation.constraints.*;
import org.chapeullah.chupapoapi.iam.authorization.model.Permission;

import java.util.Set;

public record CreateRoleRequest(
        @NotBlank(message = "Role name must not be blank")
        @Size(
                min = 5,
                max = 32,
                message = "Role name must be between 5 and 32 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9_]+$",
                message = "Role name may contain only English letters and underscores")
        String name,

        @Pattern(
                regexp = "(?s).*\\S.*",
                message = "Description must not be blank")
        @Size(
                max = 256,
                message = "Description must not exceed 256 characters")
        String description,

        @NotEmpty(message = "At least one permission must be specified")
        Set<@NotNull(message = "Permission must not be null") Permission> permissions) {}
