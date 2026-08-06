package org.chapeullah.chupapoapi.project.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.project.application.ProjectService;
import org.chapeullah.chupapoapi.project.dto.CreateProjectRequest;
import org.chapeullah.chupapoapi.project.dto.ProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAuthority('PROJECTS_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request) {
        return projectService.createProject(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PROJECTS_READ')")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse getProject(
            @PathVariable(name = "id") Long projectId) {
        return projectService.getProject(projectId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PROJECTS_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @PathVariable(name = "id") Long projectId) {
        projectService.deleteProject(projectId);
    }

}
