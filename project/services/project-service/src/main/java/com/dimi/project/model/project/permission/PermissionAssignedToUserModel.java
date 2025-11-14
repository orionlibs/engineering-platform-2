package com.dimi.project.model.project.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
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
@Table(name = "permissions_assigned_to_users", schema = "omnieng", indexes = {
                @Index(name = "idx_permissions_assigned_to_users", columnList = "id")
}, uniqueConstraints = {
                @UniqueConstraint(
                                name = "unique_permissions_assigned_to_users",
                                columnNames = {"permission_id", "user_id"}
                )
})
@Getter
@Setter
public class PermissionAssignedToUserModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionModel permission;
    @Column(name = "user_id", nullable = false)
    private UUID userID;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public PermissionAssignedToUserModel()
    {
    }


    public PermissionAssignedToUserModel(PermissionModel permission, UUID userID)
    {
        this.permission = permission;
        this.userID = userID;
    }
}
