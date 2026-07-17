package org.chapeullah.chupapoapi.iam.access.exception;

public class RoleAlreadyExistsException extends RuntimeException {

    public RoleAlreadyExistsException(String roleName) {
        super("Role '" + roleName + "' already exists");
    }
}
