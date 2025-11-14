package com.dimi.project.model.project.group;

import com.dimi.project.model.project.ProjectModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectGroupMembersDAO extends JpaRepository<ProjectGroupMemberModel, UUID>
{
    List<ProjectGroupMemberModel> findByProjectGroupId(UUID projectGroupId);


    Optional<ProjectGroupMemberModel> findByProjectAndProjectGroup(ProjectModel project, ProjectGroupModel projectGroup);


    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
