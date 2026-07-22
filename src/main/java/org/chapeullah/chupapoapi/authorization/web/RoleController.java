package org.chapeullah.chupapoapi.authorization.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.authorization.application.RoleService;
import org.chapeullah.chupapoapi.authorization.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLES_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse createRole(
            @Valid @RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLES_READ')")
    @ResponseStatus(HttpStatus.OK)
    public Page<RoleSummaryResponse> getRoles(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {
        return roleService.getRoles(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_READ')")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse getRole(@PathVariable(name = "id") Long roleId) {
        return roleService.getRole(roleId);
    }

    @PutMapping("/{id}/name")
    @PreAuthorize("hasAuthority('ROLES_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse updateRoleName(
            @PathVariable(name = "id") Long roleId,
            @Valid @RequestBody UpdateRoleNameRequest request) {
        return roleService.updateRoleName(roleId, request);
    }

    @PutMapping("/{id}/description")
    @PreAuthorize("hasAuthority('ROLES_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse updateDescription(
            @PathVariable(name = "id") Long roleId,
            @Valid @RequestBody UpdateRoleDescriptionRequest request) {
        return roleService.updateRoleDescription(roleId, request);
    }

    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('ROLES_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse updatePermissions(
            @PathVariable(name = "id") Long roleId,
            @Valid @RequestBody UpdateRolePermissionsRequest request) {
        return roleService.updateRolePermissions(roleId, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable(name = "id") Long roleId) {
        roleService.deleteRole(roleId);
    }

}
