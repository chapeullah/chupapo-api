package org.chapeullah.chupapoapi.iam.authorization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateRoleDescriptionRequest(
        @NotBlank(message = "Description must not be blank")
        @Size(
                max = 255,
                message = "Description must not exceed 255 characters")
        String description) {}
