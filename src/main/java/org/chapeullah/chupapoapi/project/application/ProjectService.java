package org.chapeullah.chupapoapi.project.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chapeullah.chupapoapi.project.dto.CreateProjectRequest;
import org.chapeullah.chupapoapi.project.dto.ProjectResponse;
import org.chapeullah.chupapoapi.project.dto.UpdateProjectRequest;
import org.chapeullah.chupapoapi.project.exception.ProjectAlreadyExistsException;
import org.chapeullah.chupapoapi.project.exception.ProjectNotFoundException;
import org.chapeullah.chupapoapi.project.model.Project;
import org.chapeullah.chupapoapi.project.repository.ProjectRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponse createProject(@Valid CreateProjectRequest request) {
        if (projectRepository.existsBySlug(request.slug()))
            throw new ProjectAlreadyExistsException(request.slug());
        Project project = new Project(
                request.slug(),
                request.authorName(),
                request.authorUrl(),
                request.repositoryUrl(),
                request.releaseDate());
        request.tags().forEach(project::addTag);
        request.previews().forEach(
                preview -> project.addPreview(
                        preview.language(),
                        preview.theme(),
                        preview.imageUrl()));
        Project savedProject = saveProject(project);
        log.info("Project created: projectId={}", savedProject.getId());
        return ProjectResponse.from(savedProject);
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponse> getProjects(
            @NotNull(message = "Pageable must not be null") Pageable pageable) {
        log.debug(
                "Getting projects: page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());
        return projectRepository.findAll(pageable).map(ProjectResponse::from);
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProject(
            @NotNull(message = "Project ID must not be null")
            @Positive(message = "Project ID must be positive")
            Long projectId) {
        log.debug("Getting project: projectId={}", projectId);
        return ProjectResponse.from(findProject(projectId));
    }

    @Transactional
    public ProjectResponse updateProject(
            @NotNull(message = "Project ID must not be null")
            @Positive(message = "Project ID must be positive")
            Long projectId,
            @Valid UpdateProjectRequest request) {
        Project project = findProject(projectId);
        if (request.slug() != null && !request.slug().equals(project.getSlug())) {
            if (projectRepository.existsBySlug(request.slug()))
                throw new ProjectAlreadyExistsException(request.slug());
            project.setSlug(request.slug());
        }
        if (request.authorName() != null &&
                !request.authorName().equals(project.getAuthorName()))
            project.setAuthorName(request.authorName());
        if (request.authorUrl() != null &&
                !request.authorUrl().equals(project.getAuthorUrl()))
            project.setAuthorUrl(request.authorUrl());
        if (request.repositoryUrl() != null &&
                !request.repositoryUrl().equals(project.getRepositoryUrl()))
            project.setRepositoryUrl(request.repositoryUrl());
        if (request.releaseDate() != null &&
                !request.releaseDate().equals(project.getReleaseDate()))
            project.setReleaseDate(request.releaseDate());
        if (request.tags() != null)
            project.updateTags(request.tags());
        if (request.previews() != null)
            project.updatePreviews(request.previews());
        log.info("Project updated: projectId={}", projectId);
        return ProjectResponse.from(saveProject(project));
    }

    @Transactional
    public void deleteProject(
            @NotNull(message = "Project ID must not be null")
            @Positive(message = "Project ID must be positive")
            Long projectId) {
        if (!projectRepository.existsById(projectId))
            throw new ProjectNotFoundException(projectId);
        projectRepository.deleteById(projectId);
        log.info("Project deleted: projectId={}", projectId);
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    private Project saveProject(Project project) {
        try {
            return projectRepository.saveAndFlush(project);
        } catch (DataIntegrityViolationException exception) {
            if (isConstraintViolation(exception)) {
                throw new ProjectAlreadyExistsException(project.getSlug());
            }
            throw exception;
        }
    }

    private boolean isConstraintViolation(
            Throwable exception) {
        Throwable cause = exception;
        while (cause != null) {
            if (cause instanceof
                    ConstraintViolationException violation &&
                    "uq_projects_slug".equals(violation.getConstraintName()))
                return true;
            cause = cause.getCause();
        }
        return false;
    }

}
