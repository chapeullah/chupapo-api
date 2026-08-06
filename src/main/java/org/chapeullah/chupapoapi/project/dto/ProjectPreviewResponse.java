package org.chapeullah.chupapoapi.project.dto;

import org.chapeullah.chupapoapi.project.model.Language;
import org.chapeullah.chupapoapi.project.model.ProjectPreview;
import org.chapeullah.chupapoapi.project.model.Theme;

public record ProjectPreviewResponse(
        Long id,
        Language language,
        Theme theme,
        String imageUrl) {
    public static ProjectPreviewResponse from(ProjectPreview preview) {
        return new ProjectPreviewResponse(
                preview.getId(),
                preview.getLanguage(),
                preview.getTheme(),
                preview.getImageUrl());
    }
}