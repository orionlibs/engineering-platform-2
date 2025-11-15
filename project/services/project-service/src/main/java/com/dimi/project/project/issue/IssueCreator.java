package com.dimi.project.project.issue;

import com.dimi.core.exception.AError;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.issue.IssueModel;
import com.dimi.project.model.project.issue.IssuesDAO;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.issue.request.CreateIssueRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class IssueCreator
{
    @Autowired private IssuesDAO dao;
    @Autowired private ProjectService projectService;


    @Transactional
    CreateIssueResult createIssue(CreateIssueRequest request)
    {
        Optional<ProjectModel> projectWrap = projectService.getByID(request.getProjectID());
        if(projectWrap.isPresent())
        {
            IssueModel model = new IssueModel(request.getCode(), request.getTitle(), projectWrap.get());
            IssueModel saved = dao.save(model);
            dao.flush();
            return CreateIssueResult.builder()
                            .issue(saved)
                            .build();
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(IssueError.PROJECT_NOT_FOUND);
            return CreateIssueResult.builder()
                            .error(error)
                            .build();
        }
    }
}
