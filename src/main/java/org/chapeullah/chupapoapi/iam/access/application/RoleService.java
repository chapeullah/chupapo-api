package org.chapeullah.chupapoapi.iam.access.application;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.exception.RoleAlreadyExistsException;
import org.chapeullah.chupapoapi.iam.access.exception.RoleNotFoundException;
import org.chapeullah.chupapoapi.iam.access.model.Permission;
import org.chapeullah.chupapoapi.iam.access.model.Role;
import org.chapeullah.chupapoapi.iam.access.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role createRole(String name, String description, Set<Permission> permissions) {
        if (roleRepository.existsById(name)) {
            throw new RoleAlreadyExistsException(name);
        }
        return roleRepository.save(new Role(name, description, permissions));
    }

    @Transactional
    public Role ensureRole(String name, String description, Set<Permission> permissions) {
        return roleRepository.findById(name)
                .map(role -> {
                    role.update(description, permissions);
                    return role;
                })
                .orElseGet(() -> roleRepository.save(new Role(name, description, permissions)));
    }

    @Transactional(readOnly = true)
    public Role getRole(String name) {
        return roleRepository.findById(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }
}
