package org.chapeullah.chupapoapi.authorization.dto;

import org.chapeullah.chupapoapi.authorization.model.Role;

public record RoleSummaryResponse(
        Long id,
        String name,
        String description) {

    public static RoleSummaryResponse from(Role role) {
        return new RoleSummaryResponse(
                role.getId(),
                role.getName(),
                role.getDescription());
    }

}
