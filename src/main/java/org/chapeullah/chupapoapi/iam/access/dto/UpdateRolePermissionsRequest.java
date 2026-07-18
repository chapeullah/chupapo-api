package org.chapeullah.chupapoapi.iam.access.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.chapeullah.chupapoapi.iam.access.model.Permission;

import java.util.Set;

public record UpdateRolePermissionsRequest(
        @NotEmpty(message = "At least one permission must be specified")
        Set<@NotNull(message = "Permission must not be null") Permission> permissions) {}