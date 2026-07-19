package org.chapeullah.chupapoapi.iam.authorization.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("Role with id '" + id + "' not found");
    }

    public RoleNotFoundException(String roleName) {
        super("Role with name '" + roleName + "' not found");
    }

}
