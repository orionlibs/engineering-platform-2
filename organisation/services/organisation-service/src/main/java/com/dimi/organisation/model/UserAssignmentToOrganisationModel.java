package com.dimi.organisation.model;

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
@Table(name = "user_assignments_to_organisations", schema = "omnieng", indexes = {
                @Index(name = "idx_user_assignments_to_organisations", columnList = "id")
}, uniqueConstraints = {
                @UniqueConstraint(
                                name = "unique_user_assignments_to_organisations",
                                columnNames = {"organisation_id", "user_id"}
                )
})
@Getter
@Setter
public class UserAssignmentToOrganisationModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userID;
    @ManyToOne(optional = false)
    @JoinColumn(name = "organisation_id", nullable = false)
    private OrganisationModel organisation;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserAssignmentToOrganisationModel()
    {
    }


    public UserAssignmentToOrganisationModel(OrganisationModel organisation, UUID userID)
    {
        this.organisation = organisation;
        this.userID = userID;
    }
}
