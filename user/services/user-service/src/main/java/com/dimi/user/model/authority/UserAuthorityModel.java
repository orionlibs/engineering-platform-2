package com.dimi.user.model.authority;

import com.dimi.user.model.user.UserModel;
import com.dimi.user.model.permission.UserPermissionsPerAuthorityModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
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
@Table(name = "user_authorities", schema = "omnieng", indexes = {
                @Index(name = "idx_user_authorities", columnList = "id")
})
@Getter
@Setter
public class UserAuthorityModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 40, nullable = false, unique = true)
    private String authority;
    @OneToMany(
                    mappedBy = "authority",
                    cascade = CascadeType.REMOVE,
                    orphanRemoval = true
    )
    private List<UserPermissionsPerAuthorityModel> permissions = new ArrayList<>();
    @ManyToMany(
                    mappedBy = "authorities",
                    cascade = CascadeType.REMOVE
    )
    private List<UserModel> users = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserAuthorityModel()
    {
    }


    public UserAuthorityModel(String authority)
    {
        this.authority = authority;
    }
}
