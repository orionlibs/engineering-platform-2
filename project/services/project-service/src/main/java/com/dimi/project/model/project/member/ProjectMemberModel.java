package com.dimi.project.model.project.member;

import com.dimi.project.model.project.ProjectModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "project_members", schema = "omnieng", indexes = {
                @Index(name = "idx_project_members", columnList = "id")
}, uniqueConstraints = {
                @UniqueConstraint(
                                name = "unique_project_members",
                                columnNames = {"user_id", "project_id"}
                )
})
@Getter
@Setter
public class ProjectMemberModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userID;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private ProjectModel project;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public ProjectMemberModel()
    {
    }


    public ProjectMemberModel(UUID userID, ProjectModel project)
    {
        this.userID = userID;
        this.project = project;
    }
}
