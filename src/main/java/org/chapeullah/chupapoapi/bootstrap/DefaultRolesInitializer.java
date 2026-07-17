package org.chapeullah.chupapoapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.application.RoleService;
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
        roleService.ensureRole(
                "ADMIN",
                "Full access",
                Set.of(Permission.values()));

        roleService.ensureRole(
                "VIEWER",
                "Read-only access",
                Set.of(
                        Permission.ACCOUNTS_READ,
                        Permission.ROLES_READ));
    }
}
