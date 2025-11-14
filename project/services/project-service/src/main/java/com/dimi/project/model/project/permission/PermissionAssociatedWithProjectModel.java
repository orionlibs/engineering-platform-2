package com.dimi.project.model.project.permission;

import com.dimi.project.model.project.ProjectModel;
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
@Table(name = "permissions_associated_with_projects", schema = "omnieng", indexes = {
                @Index(name = "idx_permissions_associated_with_projects", columnList = "id")
}, uniqueConstraints = {
                @UniqueConstraint(
                                name = "unique_permissions_associated_with_projects",
                                columnNames = {"permission_id", "project_id"}
                )
})
@Getter
@Setter
public class PermissionAssociatedWithProjectModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionModel permission;
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectModel project;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public PermissionAssociatedWithProjectModel()
    {
    }


    public PermissionAssociatedWithProjectModel(PermissionModel permission, ProjectModel project)
    {
        this.permission = permission;
        this.project = project;
    }
}
