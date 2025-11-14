package com.dimi.project.model.user_group;

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
@Table(name = "user_groups", schema = "omnieng", indexes = {
                @Index(name = "idx_user_groups", columnList = "id")
})
@Getter
@Setter
public class UserGroupModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 50, nullable = false)
    private String name;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserInUserGroupModel> userAssignedToUserGroup = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserGroupModel()
    {
    }


    public UserGroupModel(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
}
