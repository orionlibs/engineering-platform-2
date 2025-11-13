package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.ProjectError;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.UpdateProjectAvatarResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UpdateProjectAvatarAPI
{
    @Autowired ProjectService projectService;


    @PatchMapping(value = "/projects/{projectID}/avatars")
    public ResponseEntity<APIResponse> updateProjectAvatar(@PathVariable(name = "projectID") UUID projectID, @Valid @RequestBody ProjectAvatarRequest request)
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


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectAvatarRequest implements Serializable
    {
        @NotBlank(message = "avatarURL must not be blank")
        @Pattern(regexp = "https?://[\\w.-]+(?:\\.[\\w.-]+)+[/\\w\\-._~:?#[\\\\]@!$&'()*+,;=]*")
        private String avatarURL;
    }
}
