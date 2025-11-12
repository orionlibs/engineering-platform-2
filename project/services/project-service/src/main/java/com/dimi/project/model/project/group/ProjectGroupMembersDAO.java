package com.dimi.project.model.project.group;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectGroupMembersDAO extends JpaRepository<ProjectGroupMemberModel, UUID>
{
    List<ProjectGroupMemberModel> findByProjectGroupId(UUID projectGroupId);


    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
