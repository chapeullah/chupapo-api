package org.chapeullah.chupapoapi.project.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Table(name = "project_previews")
public class ProjectPreview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme", nullable = false)
    private Theme theme;

    @Column(
            name = "image_url",
            nullable = false,
            columnDefinition = "text")
    private String imageUrl;

    public ProjectPreview(
            Project project,
            Language language,
            Theme theme,
            String imageUrl) {
        this.project = project;
        this.language = language;
        this.theme = theme;
        this.imageUrl = imageUrl;
    }
}
