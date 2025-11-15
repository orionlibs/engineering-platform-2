package com.dimi.project.model.project.issue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueAssignmentsDAO extends JpaRepository<IssueAssignmentModel, UUID>
{
    Optional<IssueAssignmentModel> findByUserID(UUID userID);


    Optional<IssueAssignmentModel> findByIssue(IssueModel issue);


    List<IssueAssignmentModel> findAllByUserID(UUID userID);


    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
