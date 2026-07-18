package org.chapeullah.chupapoapi.iam.access.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.application.RoleService;
import org.chapeullah.chupapoapi.iam.access.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse createRole(
            @Valid @RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse getRole(@PathVariable(name = "id") Long roleId) {
        return roleService.getRole(roleId);
    }

    @PutMapping("/{id}/name")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse updateRoleName(
            @PathVariable(name = "id") Long roleId,
            @Valid @RequestBody UpdateRoleNameRequest request) {
        return roleService.updateRoleName(roleId, request);
    }

    @PutMapping("/{id}/description")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse updateDescription(
            @PathVariable(name = "id") Long roleId,
            @Valid @RequestBody UpdateRoleDescriptionRequest request) {
        return roleService.updateRoleDescription(roleId, request);
    }

    @PutMapping("/{id}/permissions")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse updatePermissions(
            @PathVariable(name = "id") Long roleId,
            @Valid @RequestBody UpdateRolePermissionsRequest request) {
        return roleService.updateRolePermissions(roleId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable(name = "id") Long roleId) {
        roleService.deleteRole(roleId);
    }

}
