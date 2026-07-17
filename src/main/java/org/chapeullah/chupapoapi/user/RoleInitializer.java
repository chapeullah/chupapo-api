package org.chapeullah.chupapoapi.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.user.model.Permission;
import org.chapeullah.chupapoapi.user.model.Role;
import org.chapeullah.chupapoapi.user.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(1)
public class RoleInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Set<Permission> adminPermissions = Set.of(
                Permission.USERS_CREATE,
                Permission.USERS_READ,
                Permission.USERS_UPDATE,
                Permission.USERS_DELETE,
                Permission.ROLES_CREATE,
                Permission.ROLES_READ,
                Permission.ROLES_UPDATE,
                Permission.ROLES_DELETE);
        Set<Permission> viewerPermissions = Set.of(
                Permission.USERS_READ,
                Permission.ROLES_READ);
        createRole("ADMIN", "Full", adminPermissions);
        createRole("VIEWER", "Read", viewerPermissions);
    }

    private void createRole(String name, String description, Set<Permission> permissions) {
        if (!roleRepository.existsById(name)) {
            roleRepository.save(new Role(name, description, permissions));
        }
    }

}
