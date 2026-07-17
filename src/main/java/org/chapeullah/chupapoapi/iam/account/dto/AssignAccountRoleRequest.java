package org.chapeullah.chupapoapi.iam.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssignAccountRoleRequest(
        @NotBlank(message = "Role name must not be blank")
        @Size(max = 32, message = "Role name must not exceed 32 characters")
        String roleName) {}
