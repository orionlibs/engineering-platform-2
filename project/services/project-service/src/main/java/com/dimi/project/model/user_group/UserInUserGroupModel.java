package com.dimi.project.model.user_group;

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
@Table(name = "users_in_user_groups", schema = "omnieng", indexes = {
                @Index(name = "idx_omnieng_users_in_user_groups", columnList = "id")
}, uniqueConstraints = {
                @UniqueConstraint(
                                name = "unique_users_in_user_groups",
                                columnNames = {"user_group_id", "user_id"}
                )
})
@Getter
@Setter
public class UserInUserGroupModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", length = 50, nullable = false)
    private UUID userID;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_group_id", nullable = false)
    private UserGroupModel userGroup;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserInUserGroupModel()
    {
    }


    public UserInUserGroupModel(UUID userID, UserGroupModel userGroup)
    {
        this.userID = userID;
        this.userGroup = userGroup;
    }
}
