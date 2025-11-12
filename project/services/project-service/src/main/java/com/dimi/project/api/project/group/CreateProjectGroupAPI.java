package com.dimi.project.api.project.group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.group.CreateProjectGroupResult;
import com.dimi.project.project.group.ProjectGroupError;
import com.dimi.project.project.group.ProjectGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateProjectGroupAPI
{
    @Autowired ProjectGroupService projectGroupService;


    @PostMapping(value = "/projects/groups")
    public ResponseEntity<APIResponse> createProjectGroup(@Valid @RequestBody NewProjectGroupRequest request)
    {
        CreateProjectGroupResult result = projectGroupService.createProjectGroup(request);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(ProjectGroupError.PROJECT_GROUP_ALREADY_EXISTS))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class NewProjectGroupRequest implements Serializable
    {
        @NotBlank(message = "name must not be blank")
        private String name;
        private String description;
    }
}
