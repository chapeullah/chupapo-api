package org.chapeullah.chupapoapi.project.exception;

public class ProjectAlreadyExistsException extends RuntimeException {

    public ProjectAlreadyExistsException(String slug) {
        super("Project with slug '" + slug + "' already exists");
    }

    public ProjectAlreadyExistsException(Long id) {
        super("Project with id '" + id + "' already exists");
    }

}