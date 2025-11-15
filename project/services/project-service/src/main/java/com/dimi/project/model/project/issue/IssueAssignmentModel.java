package com.dimi.project.model.project.issue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "issue_assignments", schema = "omnieng", indexes = {
                @Index(name = "idx_issue_assignments", columnList = "id")
}, uniqueConstraints = {
                @UniqueConstraint(
                                name = "unique_issue_assignments",
                                columnNames = {"user_id", "issue_id"}
                )
})
@Getter
@Setter
public class IssueAssignmentModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userID;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issue_id")
    private IssueModel issue;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public IssueAssignmentModel()
    {
    }


    public IssueAssignmentModel(UUID userID, IssueModel issue)
    {
        this.userID = userID;
        this.issue = issue;
    }
}
