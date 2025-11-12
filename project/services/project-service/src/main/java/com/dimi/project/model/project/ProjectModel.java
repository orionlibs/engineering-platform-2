package com.dimi.project.model.project;

import com.dimi.project.model.project.group.ProjectGroupMemberModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "projects", schema = "omnieng", indexes = {
                @Index(name = "idx_omnieng_projects", columnList = "id")
})
@Getter
@Setter
public class ProjectModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 10, nullable = false, unique = true)
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", length = 20, nullable = false)
    private ProjectType.Type type;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String avatarURL;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectGroupMemberModel> projectGroupsAsMember = new ArrayList<>();
    @Column(name = "manager_user_id", length = 50)
    private UUID managerUserID;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public ProjectModel()
    {
    }


    public ProjectModel(String name, String code, ProjectType.Type type, String description, String avatarURL)
    {
        this.name = name;
        this.code = code;
        this.type = type;
        this.description = description;
        this.avatarURL = avatarURL;
    }
}
