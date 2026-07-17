package org.chapeullah.chupapoapi.iam.account.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String username) {
        super("Account with username '" + username + "' already exists");
    }

}
