package org.chapeullah.chupapoapi.project.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.project.application.ProjectService;
import org.chapeullah.chupapoapi.project.dto.CreateProjectRequest;
import org.chapeullah.chupapoapi.project.dto.ProjectResponse;
import org.chapeullah.chupapoapi.project.dto.UpdateProjectRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    @PreAuthorize("hasAuthority('PROJECTS_READ')")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjectResponse> getProjects(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {
        return projectService.getProjects(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PROJECTS_READ')")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse getProject(
            @PathVariable(name = "id") Long projectId) {
        return projectService.getProject(projectId);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('PROJECTS_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse updateProject(
            @PathVariable(name = "id") Long projectId,
            @Valid @RequestBody UpdateProjectRequest request) {
        return projectService.updateProject(projectId, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PROJECTS_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @PathVariable(name = "id") Long projectId) {
        projectService.deleteProject(projectId);
    }

}
