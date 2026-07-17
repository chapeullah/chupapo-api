package org.chapeullah.chupapoapi.iam.access.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    private Set<Permission> permissions = new HashSet<>();

    public Role(String name, String description, Set<Permission> permissions) {
        this.name = name;
        this.description = description;
        this.permissions = new HashSet<>(permissions);
    }

    public void update(String description, Set<Permission> permissions) {
        this.description = description;
        this.permissions.clear();
        this.permissions.addAll(permissions);
    }
}
