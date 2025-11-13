package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.ProjectError;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.UpdateProjectManagerResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class UpdateProjectManagerAPI
{
    @Autowired private ProjectService projectService;


    @PatchMapping(value = "/projects/{projectID}/managers")
    public ResponseEntity<APIResponse> updateProjectManage(@PathVariable(name = "projectID") UUID projectID, @Valid @RequestBody ProjectManagerRequest request)
    {
        UpdateProjectManagerResult result = projectService.updateManager(projectID, request.getManagerUserID());
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


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectManagerRequest implements Serializable
    {
        @NotBlank(message = "managerUserID must not be blank")
        private UUID managerUserID;
    }
}
