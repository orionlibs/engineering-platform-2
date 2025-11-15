package com.dimi.project.model.project.issue;

import com.dimi.project.model.project.ProjectModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "issues", schema = "omnieng", indexes = {
                @Index(name = "idx_issues", columnList = "id")
}, uniqueConstraints = {
                @UniqueConstraint(
                                name = "unique_issues",
                                columnNames = {"id", "code"}
                )
})
@Getter
@Setter
public class IssueModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 10, nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private ProjectModel project;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public IssueModel()
    {
    }


    public IssueModel(String title, String code, ProjectModel project)
    {
        this.title = title;
        this.code = code;
        this.project = project;
    }
}
