package org.chapeullah.chupapoapi.iam.account.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chapeullah.chupapoapi.iam.authorization.model.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(
            name = "username",
            length = 32,
            unique = true,
            nullable = false, check = {
                    @CheckConstraint(
                            name = "ck_accounts_username_min_length",
                            constraint = "char_length(username) >= 5"),
                    @CheckConstraint(
                            name = "ck_accounts_username_format",
                            constraint = "username ~ '^[a-zA-Z0-9_]+$'")})
    private String username;

    @Setter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @Setter
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Account(String username, String passwordHash, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

}
