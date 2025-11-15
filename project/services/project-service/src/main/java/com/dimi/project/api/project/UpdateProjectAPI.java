package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.ProjectError;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.UpdateProjectResult;
import com.dimi.project.project.request.UpdateProjectRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateProjectAPI
{
    @Autowired private ProjectService projectService;


    @PutMapping(value = "/projects/{projectID}")
    public ResponseEntity<APIResponse> updateProject(@PathVariable(name = "projectID") UUID projectID, @Valid @RequestBody UpdateProjectRequest request)
    {
        UpdateProjectResult result = projectService.updateProject(projectID, request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(ProjectError.PROJECT_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
