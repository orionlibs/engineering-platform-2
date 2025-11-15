package com.dimi.project.project.issue;

import com.dimi.project.model.project.issue.IssueAssignmentModel;
import com.dimi.project.model.project.issue.IssueAssignmentsDAO;
import com.dimi.project.model.project.issue.IssueModel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class IssueUnassigner
{
    @Autowired private IssueAssignmentsDAO dao;


    @Transactional
    UnassignIssueResult unassignIssue(IssueModel issue)
    {
        Optional<IssueAssignmentModel> modelWrap = dao.findByIssue(issue);
        if(modelWrap.isPresent())
        {
            dao.delete(modelWrap.get());
            dao.flush();
        }
        return UnassignIssueResult.builder().build();
    }
}
