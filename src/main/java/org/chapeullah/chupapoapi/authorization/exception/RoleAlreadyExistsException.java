package org.chapeullah.chupapoapi.authorization.exception;

public class RoleAlreadyExistsException extends RuntimeException {

    public RoleAlreadyExistsException(Long id) {
        super("Role with id '" + id + "' already exists");
    }

    public RoleAlreadyExistsException(String roleName) {
        super("Role with name '" + roleName + "' already exists");
    }

}
