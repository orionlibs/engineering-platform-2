package com.dimi.organisation.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "organisations", schema = "omnieng", indexes = {
                @Index(name = "idx_organisations", columnList = "id")
})
@Getter
@Setter
public class OrganisationModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    @Column(name = "owner_user_id", nullable = false, unique = true)
    private UUID ownerUserID;
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserAssignmentToOrganisationModel> userAssignmentsToOrganisations = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public OrganisationModel()
    {
    }


    public OrganisationModel(String name, UUID ownerUserID)
    {
        this.name = name;
        this.ownerUserID = ownerUserID;
    }
}
