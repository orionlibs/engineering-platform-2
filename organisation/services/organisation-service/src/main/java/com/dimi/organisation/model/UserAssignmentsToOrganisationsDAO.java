package com.dimi.organisation.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAssignmentsToOrganisationsDAO extends JpaRepository<UserAssignmentToOrganisationModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    List<UserAssignmentToOrganisationModel> findAllByUserID(UUID userID);


    Optional<UserAssignmentToOrganisationModel> findByOrganisationAndUserID(OrganisationModel organisation, UUID userID);
}
