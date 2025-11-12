package com.dimi.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "users", schema = "omnieng", indexes = {
                @Index(name = "idx_omnieng_users", columnList = "id")
})
@Getter
@Setter
public class UserModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 80, nullable = false, unique = true)
    private String username;
    @ManyToMany
    @JoinTable(
                    name = "user_authority_mapping",
                    schema = "omnieng",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<UserAuthorityModel> authorities = new ArrayList<>();
    @ManyToMany
    @JoinTable(
                    name = "user_permission_mapping",
                    schema = "omnieng",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<UserPermissionsPerAuthorityModel> permissions = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserModel()
    {
    }


    public UserModel(String username, List<UserAuthorityModel> authorities)
    {
        this.username = username;
        this.authorities = authorities;
    }
}
