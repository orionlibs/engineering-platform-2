package com.dimi.project.model.project.permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsAssignedToUsersDAO extends JpaRepository<PermissionAssignedToUserModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<PermissionAssignedToUserModel> findByPermissionAndUserID(PermissionModel permission, UUID userID);


    List<PermissionAssignedToUserModel> findAllByUserID(UUID userID);


    List<PermissionAssignedToUserModel> findAllByUserIDIn(List<UUID> UserIds);


    List<PermissionAssignedToUserModel> findAllByPermissionAndUserIDIn(PermissionModel permission, List<UUID> ids);
}
