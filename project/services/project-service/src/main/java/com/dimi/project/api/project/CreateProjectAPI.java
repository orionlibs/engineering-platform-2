package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.model.project.ProjectType;
import com.dimi.project.project.CreateProjectResult;
import com.dimi.project.project.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @Autowired ProjectService projectService;


    @PostMapping(value = "/projects")
    public ResponseEntity<APIResponse> createProject(@Valid @RequestBody NewProjectRequest request)
    {
        CreateProjectResult result = projectService.createProject(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class NewProjectRequest implements Serializable
    {
        @NotBlank(message = "name must not be blank")
        private String name;
        @NotBlank(message = "code must not be blank")
        private String code;
        private String description;
        @NotNull(message = "type cannot be null")
        private ProjectType.Type type;
        private String avatarURL;
    }
}
