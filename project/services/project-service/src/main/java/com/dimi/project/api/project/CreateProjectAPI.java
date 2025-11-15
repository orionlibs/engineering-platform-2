package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.CreateProjectResult;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.request.CreateProjectRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateProjectAPI
{
    @Autowired private ProjectService projectService;


    @PostMapping(value = "/projects")
    public ResponseEntity<APIResponse> createProject(@Valid @RequestBody CreateProjectRequest request)
    {
        CreateProjectResult result = projectService.createProject(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
