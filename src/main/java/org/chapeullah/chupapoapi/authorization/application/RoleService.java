package org.chapeullah.chupapoapi.authorization.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.authorization.dto.*;
import org.chapeullah.chupapoapi.authorization.exception.RoleAlreadyExistsException;
import org.chapeullah.chupapoapi.authorization.exception.RoleNotFoundException;
import org.chapeullah.chupapoapi.authorization.model.Role;
import org.chapeullah.chupapoapi.authorization.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
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
    public Page<RoleSummaryResponse> getRoles(@NotNull Pageable pageable) {
        return roleRepository.findAll(pageable).map(RoleSummaryResponse::from);
    }

    @Transactional(readOnly = true)
    public RoleResponse getRole(@NotNull @Positive Long roleId) {
        return RoleResponse.from(findRole(roleId));
    }

    @Transactional
    public RoleResponse updateRoleName(
            @NotNull @Positive Long roleId,
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
            @NotNull @Positive Long roleId,
            @Valid UpdateRoleDescriptionRequest request) {
        Role role = findRole(roleId);
        role.setDescription(request.description());
        return RoleResponse.from(roleRepository.save(role));
    }

    @Transactional
    public RoleResponse updateRolePermissions(
            @NotNull @Positive Long roleId,
            @Valid UpdateRolePermissionsRequest request) {
        Role role = findRole(roleId);
        role.updatePermissions(request.permissions());
        return RoleResponse.from(roleRepository.save(role));
    }

    @Transactional
    public void deleteRole(@NotNull @Positive Long roleId) {
        if (!roleRepository.existsById(roleId))
            throw new RoleNotFoundException(roleId);
        roleRepository.deleteById(roleId);
    }

    private Role findRole(@NotNull @Positive Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
    }
}
