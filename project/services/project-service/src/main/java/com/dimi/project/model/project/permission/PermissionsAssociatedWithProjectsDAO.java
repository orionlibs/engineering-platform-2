package com.dimi.project.model.project.permission;

import com.dimi.project.model.project.ProjectModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsAssociatedWithProjectsDAO extends JpaRepository<PermissionAssociatedWithProjectModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<PermissionAssociatedWithProjectModel> findByPermissionAndProject(PermissionModel permission, ProjectModel project);


    List<PermissionAssociatedWithProjectModel> findAllByProject(ProjectModel project);
}
