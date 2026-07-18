package org.chapeullah.chupapoapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.application.RoleService;
import org.chapeullah.chupapoapi.iam.access.dto.CreateRoleRequest;
import org.chapeullah.chupapoapi.iam.access.model.Permission;
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
                "ADMIN",
                "Full-rights",
                Set.of(Permission.values())));
        roleService.createRole(new CreateRoleRequest(
                "VIEWER",
                "Read-only",
                Set.of(
                        Permission.ROLES_READ,
                        Permission.ACCOUNTS_READ)));
    }
}
