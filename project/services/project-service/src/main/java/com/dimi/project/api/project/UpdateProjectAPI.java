package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.model.project.ProjectType;
import com.dimi.project.project.ProjectError;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.UpdateProjectResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateProjectAPI
{
    @Autowired ProjectService projectService;


    @PutMapping(value = "/projects/{projectID}")
    public ResponseEntity<APIResponse> updateProject(@PathVariable(name = "projectID") UUID projectID, @Valid @RequestBody UpdateProjectRequest request)
    {
        UpdateProjectResult result = projectService.updateProject(projectID, request);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(ProjectError.PROJECT_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
            else if(result.getError().getErrorCode().equals(ProjectError.PROJECT_ALREADY_EXISTS))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class UpdateProjectRequest implements Serializable
    {
        @NotBlank(message = "name must not be blank")
        private String name;
        @NotBlank(message = "code must not be blank")
        private String code;
        private String description;
        @NotNull(message = "type cannot be null")
        private ProjectType.Type type;
    }
}
