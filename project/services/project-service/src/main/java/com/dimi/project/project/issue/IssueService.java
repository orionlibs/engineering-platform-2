package com.dimi.project.project.issue;

import com.dimi.project.model.project.issue.IssueModel;
import com.dimi.project.model.project.issue.IssuesDAO;
import com.dimi.project.project.issue.request.CreateIssueRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueService
{
    @Autowired private IssuesDAO dao;
    @Autowired private IssueCreator issueCreator;


    @Transactional
    public CreateIssueResult createIssue(CreateIssueRequest request)
    {
        return issueCreator.createIssue(request);
    }


    @Transactional
    public IssueModel save(IssueModel issue)
    {
        return dao.save(issue);
    }


    public Optional<IssueModel> getByID(UUID issueID)
    {
        return dao.findById(issueID);
    }


    public void delete(UUID issueID)
    {
        dao.deleteById(issueID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
