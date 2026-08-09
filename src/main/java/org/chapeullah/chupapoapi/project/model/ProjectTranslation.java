package org.chapeullah.chupapoapi.project.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chapeullah.chupapoapi.localization.model.Language;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "project_translations",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_project_translations_project_id_language",
                columnNames = {"project_id", "language"}))
public class ProjectTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false, length = 16)
    private Language language;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "project_type", nullable = false, length = 100)
    private String projectType;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    public ProjectTranslation(
            Project project,
            Language language,
            String name,
            String projectType,
            String description) {
        this.project = project;
        this.language = language;
        this.name = name;
        this.projectType = projectType;
        this.description = description;
    }

    public void updateContent(
            String name,
            String projectType,
            String description) {
        this.name = name;
        this.projectType = projectType;
        this.description = description;
    }

}
