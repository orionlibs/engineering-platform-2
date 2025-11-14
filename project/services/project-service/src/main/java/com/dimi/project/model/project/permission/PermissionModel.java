package com.dimi.project.model.project.permission;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "permissions", schema = "omnieng", indexes = {
                @Index(name = "idx_permissions", columnList = "id")
})
@Getter
@Setter
public class PermissionModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 40, nullable = false, unique = true)
    private String name;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "permission", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PermissionAssignedToUserModel> permissionAssignedToUsers = new ArrayList<>();
    @OneToMany(mappedBy = "permission", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PermissionAssociatedWithProjectModel> permissionAssociatedWithProjects = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public PermissionModel()
    {
    }


    public PermissionModel(String name)
    {
        this.name = name;
    }


    public PermissionModel(String name, String description)
    {
        this(name);
        this.description = description;
    }
}
