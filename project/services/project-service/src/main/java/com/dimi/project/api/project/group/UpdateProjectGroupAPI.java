package com.dimi.project.api.project.group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.group.ProjectGroupError;
import com.dimi.project.project.group.ProjectGroupService;
import com.dimi.project.project.group.UpdateProjectGroupResult;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateProjectGroupAPI
{
    @Autowired ProjectGroupService projectGroupService;


    @PostMapping(value = "/projects/groups/{projectGroupID}")
    public ResponseEntity<APIResponse> updateProjectGroup(@PathVariable(name = "projectGroupID") UUID projectGroupID, @Valid @RequestBody UpdateProjectGroupRequest request)
    {
        UpdateProjectGroupResult result = projectGroupService.updateProjectGroup(projectGroupID, request);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(ProjectGroupError.PROJECT_GROUP_NOT_FOUND))
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
    public static class UpdateProjectGroupRequest implements Serializable
    {
        @NotBlank(message = "name must not be blank")
        private String name;
        private String description;
    }
}
