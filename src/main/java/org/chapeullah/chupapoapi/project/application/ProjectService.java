package org.chapeullah.chupapoapi.project.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chapeullah.chupapoapi.project.dto.CreateProjectRequest;
import org.chapeullah.chupapoapi.project.dto.ProjectResponse;
import org.chapeullah.chupapoapi.project.exception.ProjectAlreadyExistsException;
import org.chapeullah.chupapoapi.project.exception.ProjectNotFoundException;
import org.chapeullah.chupapoapi.project.model.Project;
import org.chapeullah.chupapoapi.project.repository.ProjectRepository;
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
        request.tags().forEach(
                tag -> project.addTag(tag));
        request.previews().forEach(
                preview -> project.addPreview(
                        preview.language(),
                        preview.theme(),
                        preview.imageUrl()));
        Project savedProject = projectRepository.saveAndFlush(project);
        log.info("Project created: projectId={}", savedProject.getId());
        return ProjectResponse.from(savedProject);
    }

    @Transactional
    public ProjectResponse getProject(@NotNull @Positive Long projectId) {
        log.debug("Getting project: projectId={}", projectId);
        return ProjectResponse.from(findProject(projectId));
    }

    @Transactional
    public void deleteProject(@NotNull @Positive Long projectId) {
        if (!projectRepository.existsById(projectId))
            throw new ProjectNotFoundException(projectId);
        projectRepository.deleteById(projectId);
        log.info("Project deleted: projectId={}", projectId);
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

}
