package com.dimi.project.project.issue;

import com.dimi.project.model.project.issue.IssueAssignmentModel;
import com.dimi.project.model.project.issue.IssueAssignmentsDAO;
import com.dimi.project.model.project.issue.IssueModel;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class IssueAssigner
{
    @Autowired private IssueAssignmentsDAO dao;


    @Transactional
    AssignIssueResult assignIssue(IssueModel issue, UUID userID)
    {
        IssueAssignmentModel model = new IssueAssignmentModel(userID, issue);
        IssueAssignmentModel saved = dao.save(model);
        dao.flush();
        return AssignIssueResult.builder()
                        .issueAssignment(saved)
                        .build();
    }
}
