package org.chapeullah.chupapoapi.account.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String username) {
        super("Account with username '" + username + "' already exists");
    }

    public AccountAlreadyExistsException(Long id) {
        super("Account with id '" + id + "' already exists");
    }

}
