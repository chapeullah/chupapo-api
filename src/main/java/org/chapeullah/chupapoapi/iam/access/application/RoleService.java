package org.chapeullah.chupapoapi.iam.access.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.dto.*;
import org.chapeullah.chupapoapi.iam.access.exception.RoleAlreadyExistsException;
import org.chapeullah.chupapoapi.iam.access.exception.RoleNotFoundException;
import org.chapeullah.chupapoapi.iam.access.model.Role;
import org.chapeullah.chupapoapi.iam.access.repository.RoleRepository;
import org.chapeullah.chupapoapi.iam.account.exception.AccountAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse createRole(@Valid CreateRoleRequest request) {
        if (roleRepository.existsByName(request.name()))
            throw new RoleAlreadyExistsException(request.name());
        Role role = new Role(
                request.name(),
                request.description(),
                request.permissions());
        return RoleResponse.from(roleRepository.save(role));
    }

    @Transactional(readOnly = true)
    public RoleResponse getRole(Long roleId) {
        return RoleResponse.from(findRole(roleId));
    }

    @Transactional
    public RoleResponse updateRoleName(
            Long roleId,
            @Valid UpdateRoleNameRequest request) {
        Role role = findRole(roleId);
        if (!role.getName().equals(request.name())
                && roleRepository.existsByName(request.name()))
            throw new RoleAlreadyExistsException(request.name());
        role.setName(request.name());
        return RoleResponse.from(roleRepository.save(role));
    }

    @Transactional
    public RoleResponse updateRoleDescription(
            Long roleId,
            @Valid UpdateRoleDescriptionRequest request) {
        Role role = findRole(roleId);
        role.setDescription(request.description());
        return RoleResponse.from(roleRepository.save(role));
    }

    @Transactional
    public RoleResponse updateRolePermissions(
            Long roleId,
            UpdateRolePermissionsRequest request) {
        Role role = findRole(roleId);
        role.updatePermissions(request.permissions());
        return RoleResponse.from(roleRepository.save(role));
    }

    @Transactional
    public void deleteRole(Long roleId) {
        if (roleRepository.existsById(roleId))
            throw new RoleNotFoundException(roleId);
        roleRepository.deleteById(roleId);
    }

    private Role findRole(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
    }
}
