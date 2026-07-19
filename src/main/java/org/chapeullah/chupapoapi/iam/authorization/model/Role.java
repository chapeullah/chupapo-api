package org.chapeullah.chupapoapi.iam.authorization.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(
            name = "name",
            unique = true,
            nullable = false,
            length = 32,
            check = {
                    @CheckConstraint(
                            name = "ck_roles_name_min_length",
                            constraint = "char_length(name) >= 5"),
                    @CheckConstraint(
                            name = "ck_roles_name_format",
                            constraint = "name ~ '^[a-zA-Z0-9_]+$'")})
    private String name;

    @Setter
    @Column(
            name = "description",
            length = 256)
    private String description;

    @SuppressWarnings("FieldMayBeFinal")
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
        this.permissions.addAll(permissions);
    }

    public void updatePermissions(Set<Permission> permissions) {
        this.permissions.clear();
        this.permissions.addAll(permissions);
    }

}
