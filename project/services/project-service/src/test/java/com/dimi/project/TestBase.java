package com.dimi.project;

import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType;
import com.dimi.project.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBase
{
    @Autowired public ProjectService projectService;
    protected String apiEndpointPrefix = "http://localhost:";


    protected void addPortToAPIEndpoint(int port)
    {
        this.apiEndpointPrefix += port;
    }


    protected ProjectModel saveProject(String name, String code, ProjectType.Type type, String description, String avatarURL)
    {
        ProjectModel project = new ProjectModel(name, code, type, description, avatarURL);
        return projectService.save(project);
    }
}
