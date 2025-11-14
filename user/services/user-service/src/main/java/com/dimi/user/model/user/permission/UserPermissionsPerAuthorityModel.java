package com.dimi.user.model.user.permission;

import com.dimi.user.model.user.authority.UserAuthorityModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
                name = "user_permissions_per_authority",
                schema = "omnieng",
                indexes = {
                                @Index(name = "idx_user_permissions_per_authority", columnList = "id")
                },
                uniqueConstraints = {
                                @UniqueConstraint(
                                                name = "unique_permissions_per_authority",
                                                columnNames = {"permission_id", "authority_id"}
                                )
                }
)
@Getter
@Setter
public class UserPermissionsPerAuthorityModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
                    name = "permission_id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "fk_user_permissions_per_authority_permission")
    )
    private UserPermissionModel permission;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
                    name = "authority_id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "fk_user_permissions_per_authority_authority")
    )
    private UserAuthorityModel authority;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserPermissionsPerAuthorityModel()
    {
    }


    public UserPermissionsPerAuthorityModel(UserPermissionModel permission, UserAuthorityModel authority)
    {
        this.permission = permission;
        this.authority = authority;
    }
}
