package org.chapeullah.chupapoapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.authorization.application.RoleService;
import org.chapeullah.chupapoapi.authorization.dto.CreateRoleRequest;
import org.chapeullah.chupapoapi.authorization.model.Permission;
import org.chapeullah.chupapoapi.authorization.model.Role;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(1)
@RequiredArgsConstructor
public class DefaultRolesInitializer implements ApplicationRunner {

    private final RoleService roleService;

    @Override
    public void run(ApplicationArguments args) {
        roleService.createRole(new CreateRoleRequest(
                "ADMIN", "Full-rights", Set.of(Permission.values())));
        roleService.createRole(new CreateRoleRequest("VIEWER", "Read-only", Set.of(
                Permission.ROLES_READ, Permission.ACCOUNTS_READ, Permission.PROJECTS_READ)));
    }
}
