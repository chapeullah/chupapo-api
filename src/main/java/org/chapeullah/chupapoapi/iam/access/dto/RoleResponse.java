package org.chapeullah.chupapoapi.iam.access.dto;

import org.chapeullah.chupapoapi.iam.access.model.Permission;
import org.chapeullah.chupapoapi.iam.access.model.Role;

import java.util.Set;

public record RoleResponse(
        Long id,
        String name,
        String description,
        Set<Permission> permissions) {

    public static RoleResponse from(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                Set.copyOf(role.getPermissions()));
    }

}
