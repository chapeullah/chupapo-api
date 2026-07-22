package org.chapeullah.chupapoapi.authorization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateRoleDescriptionRequest(
        @NotBlank(message = "Description must not be blank")
        @Size(
                max = 256,
                message = "Description must not exceed 256 characters")
        String description) {}
