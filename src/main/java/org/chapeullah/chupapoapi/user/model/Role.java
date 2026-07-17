package org.chapeullah.chupapoapi.user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
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

    @ElementCollection
    @CollectionTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    @Column(name = "permission", nullable = false)
    private Set<Permission> permissions = new HashSet<>();

    public Role(String name, String description, Set<Permission> permissions) {
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    public void updatePermissions(Set<Permission> permissions) {
        this.permissions.clear();
        this.permissions.addAll(permissions);
    }

}
