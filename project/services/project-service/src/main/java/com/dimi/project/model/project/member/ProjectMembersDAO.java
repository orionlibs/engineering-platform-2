package com.dimi.project.model.project.member;

import com.dimi.project.model.project.ProjectModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMembersDAO extends JpaRepository<ProjectMemberModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<ProjectMemberModel> findByProjectAndUserID(ProjectModel project, UUID userId);


    List<ProjectMemberModel> findAllByUserID(UUID userId);
}
