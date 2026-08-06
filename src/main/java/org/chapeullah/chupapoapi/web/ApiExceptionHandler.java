package org.chapeullah.chupapoapi.web;

import org.chapeullah.chupapoapi.authorization.exception.RoleAlreadyExistsException;
import org.chapeullah.chupapoapi.authorization.exception.RoleNotFoundException;
import org.chapeullah.chupapoapi.account.exception.AccountAlreadyExistsException;
import org.chapeullah.chupapoapi.account.exception.AccountNotFoundException;

import org.chapeullah.chupapoapi.project.exception.ProjectAlreadyExistsException;
import org.chapeullah.chupapoapi.project.exception.ProjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            AccountNotFoundException.class,
            RoleNotFoundException.class,
            ProjectNotFoundException.class
    })
    public ProblemDetail handleNotFound(RuntimeException exception) {
        return problem(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({
            AccountAlreadyExistsException.class,
            RoleAlreadyExistsException.class,
            ProjectAlreadyExistsException.class
    })
    public ProblemDetail handleAlreadyExists(RuntimeException exception) {
        return problem(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationFailure(AuthenticationException exception) {
        return problem(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

    private ProblemDetail problem(HttpStatus status, String message) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, message);
        problem.setTitle(status.getReasonPhrase());
        return problem;
    }

}
