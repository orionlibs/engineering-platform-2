package com.dimi.project.project.issue;

import com.dimi.core.exception.AError;
import com.dimi.project.model.project.issue.IssueAssignmentsDAO;
import com.dimi.project.model.project.issue.IssueModel;
import com.dimi.project.model.project.issue.IssuesDAO;
import com.dimi.project.project.issue.request.CreateIssueRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueService
{
    @Autowired private IssuesDAO issuesDAO;
    @Autowired private IssueAssignmentsDAO issueAssignmentsDAO;
    @Autowired private IssueCreator issueCreator;
    @Autowired private IssueAssigner issueAssigner;
    @Autowired private IssueUnassigner issueUnassigner;


    @Transactional
    public CreateIssueResult createIssue(CreateIssueRequest request)
    {
        return issueCreator.createIssue(request);
    }


    @Transactional
    public AssignIssueResult assignIssue(UUID issueID, UUID userID)
    {
        Optional<IssueModel> issueWrap = getByID(issueID);
        if(issueWrap.isPresent())
        {
            UnassignIssueResult unassignment = issueUnassigner.unassignIssue(issueWrap.get());
            return issueAssigner.assignIssue(issueWrap.get(), userID);
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(IssueError.ISSUE_NOT_FOUND);
            return AssignIssueResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public UnassignIssueResult unassignIssue(UUID issueID)
    {
        Optional<IssueModel> issueWrap = getByID(issueID);
        if(issueWrap.isPresent())
        {
            return issueUnassigner.unassignIssue(issueWrap.get());
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(IssueError.ISSUE_NOT_FOUND);
            return UnassignIssueResult.builder()
                            .error(error)
                            .build();
        }
    }


    public List<UUID> getAllIssueIDsAssignedToUser(UUID userID)
    {
        return issueAssignmentsDAO.findAllByUserID(userID)
                        .stream()
                        .map(a -> a.getIssue().getId())
                        .toList();
    }


    @Transactional
    public IssueModel save(IssueModel issue)
    {
        return issuesDAO.save(issue);
    }


    public Optional<IssueModel> getByID(UUID issueID)
    {
        return issuesDAO.findById(issueID);
    }


    public void delete(UUID issueID)
    {
        issuesDAO.deleteById(issueID);
    }


    public void deleteAll()
    {
        issuesDAO.deleteAll();
    }
}
