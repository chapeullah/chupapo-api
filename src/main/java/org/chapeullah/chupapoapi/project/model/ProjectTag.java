package org.chapeullah.chupapoapi.project.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "project_tags",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_project_tags_project_id_name",
                columnNames = {"project_id", "name"}))
public class ProjectTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    public ProjectTag(Project project, String name) {
        this.project = project;
        this.name = name;
    }

}
