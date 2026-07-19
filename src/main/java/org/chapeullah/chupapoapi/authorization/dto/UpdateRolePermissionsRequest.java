package org.chapeullah.chupapoapi.authorization.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.chapeullah.chupapoapi.authorization.model.Permission;

import java.util.Set;

public record UpdateRolePermissionsRequest(
        @NotEmpty(message = "At least one permission must be specified")
        Set<@NotNull(message = "Permission must not be null") Permission> permissions) {}