package org.chapeullah.chupapoapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.model.Permission;
import org.chapeullah.chupapoapi.iam.access.model.Role;
import org.chapeullah.chupapoapi.iam.access.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(1)
@RequiredArgsConstructor
public class DefaultRolesInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (!roleRepository.existsByName("ADMIN")) {
            roleRepository.save(new Role(
                    "ADMIN",
                    "Full-rights",
                    Set.of(Permission.values())));
        }
        if (!roleRepository.existsByName("VIEWER")) {
            roleRepository.save(new Role("VIEWER", "Read-only", Set.of(
                    Permission.ROLES_READ,
                    Permission.ACCOUNTS_READ)));
        }
    }
}
