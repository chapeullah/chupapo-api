package org.chapeullah.chupapoapi.user.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String roleName) {
        super("Role '" + roleName + "' not found");
    }
}
