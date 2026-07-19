package org.chapeullah.chupapoapi.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateAccountRoleRequest(
        @NotNull(message = "Role ID must not be null")
        @Positive(message = "Role ID must be positive")
        Long roleId
) {}