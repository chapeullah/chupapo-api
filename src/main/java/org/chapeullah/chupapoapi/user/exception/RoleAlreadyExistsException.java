package org.chapeullah.chupapoapi.user.exception;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String roleName) {
        super("Role '" + roleName + "' already exists");
    }
}
