package org.chapeullah.chupapoapi.project.dto;

import org.chapeullah.chupapoapi.project.model.Project;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record ProjectResponse(
        Long id,
        String slug,
        String authorName,
        String authorUrl,
        String repositoryUrl,
        LocalDate releaseDate,
        Set<String> tags,
        Set<ProjectPreviewResponse> previews,
        Instant createdAt,
        Instant updatedAt) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getSlug(),
                project.getAuthorName(),
                project.getAuthorUrl(),
                project.getRepositoryUrl(),
                project.getReleaseDate(),
                project.getTags()
                        .stream()
                        .map(projectTag -> projectTag.getName())
                        .collect(Collectors.toSet()),
                project.getPreviews()
                        .stream()
                        .map(projectPreview -> ProjectPreviewResponse.from(projectPreview))
                        .collect(Collectors.toSet()),
                project.getCreatedAt(),
                project.getUpdatedAt());
    }
}