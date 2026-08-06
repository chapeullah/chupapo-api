package org.chapeullah.chupapoapi.project.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long id) {
        super("Project with id '" + id + "' not found");
    }

    public ProjectNotFoundException(String slug) {
        super("Project with slug '" + slug + "' not found");
    }

}

