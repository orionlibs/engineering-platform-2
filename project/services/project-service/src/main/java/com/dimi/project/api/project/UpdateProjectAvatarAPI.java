package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.ProjectError;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.UpdateProjectAvatarResult;
import com.dimi.project.project.request.UpdateProjectAvatarRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateProjectAvatarAPI
{
    @Autowired private ProjectService projectService;


    @PatchMapping(value = "/projects/{projectID}/avatars")
    public ResponseEntity<APIResponse> updateProjectAvatar(@PathVariable(name = "projectID") UUID projectID, @Valid @RequestBody UpdateProjectAvatarRequest request)
    {
        UpdateProjectAvatarResult result = projectService.updateAvatar(projectID, request.getAvatarURL());
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
