package com.dimi.user.model.user.permission;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionsPerAuthorityDAO extends JpaRepository<UserPermissionsPerAuthorityModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<UserPermissionsPerAuthorityModel> findByPermission_IdAndAuthority_Id(UUID permissionId, UUID authorityId);
}
