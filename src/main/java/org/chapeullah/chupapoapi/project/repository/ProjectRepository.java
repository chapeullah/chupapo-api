package org.chapeullah.chupapoapi.project.repository;

import org.chapeullah.chupapoapi.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsBySlug(String slug);

}
