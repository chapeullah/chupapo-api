package org.chapeullah.chupapoapi.project.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chapeullah.chupapoapi.localization.model.Language;
import org.chapeullah.chupapoapi.project.dto.CreateProjectPreviewRequest;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "projects",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_projects_slug",
                columnNames = "slug"))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @Setter
    @Column(
            name = "slug",
            nullable = false,
            length = 128)
    private String slug;

    @Setter
    @Column(name = "author_name", nullable = false, length = 128)
    private String authorName;

    @Setter
    @Column(name = "author_url", nullable = false, columnDefinition = "text")
    private String authorUrl;

    @Setter
    @Column(name = "repository_url", nullable = false, columnDefinition = "text")
    private String repositoryUrl;

    @SuppressWarnings("FieldMayBeFinal")
    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProjectTranslation> translations = new HashSet<>();

    @SuppressWarnings("FieldMayBeFinal")
    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProjectTag> tags = new HashSet<>();

    @SuppressWarnings("FieldMayBeFinal")
    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProjectPreview> previews = new HashSet<>();

    @Setter
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Project(
            String slug,
            String authorName,
            String authorUrl,
            String repositoryUrl,
            LocalDate releaseDate) {
        this.slug = slug;
        this.authorName = authorName;
        this.authorUrl = authorUrl;
        this.repositoryUrl = repositoryUrl;
        this.releaseDate = releaseDate;
    }

    public void addTag(String name) {
        this.tags.add(new ProjectTag(this, name));
    }

    public void addPreview(
            Language language,
            Theme theme,
            String imageUrl) {
        this.previews.add(
                new ProjectPreview(this, language, theme, imageUrl));
    }

    // TODO
    public void updateTags(Set<String> tags) {
        this.tags.clear();
        tags.forEach(this::addTag);
    }

    // TODO
    public void updatePreviews(Set<CreateProjectPreviewRequest> previews) {
        this.previews.clear();
        previews.forEach(preview -> addPreview(
                preview.language(),
                preview.theme(),
                preview.imageUrl()));
    }

}
