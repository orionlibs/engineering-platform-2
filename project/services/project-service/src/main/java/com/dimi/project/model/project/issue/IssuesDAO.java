package com.dimi.project.model.project.issue;

import com.dimi.project.model.project.ProjectModel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuesDAO extends JpaRepository<IssueModel, UUID>
{
    List<IssueModel> findAllByProject(ProjectModel project);


    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
