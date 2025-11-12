package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.ProjectService;
import com.dimi.project.model.project.ProjectType;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectAPI
{
    @Autowired ProjectService projectService;


    @GetMapping(value = "/projects/{projectID}")
    public ResponseEntity<APIResponse> getProject(@PathVariable(name = "projectID") UUID projectID)
    {
        APIResponse<ProjectResponse> response = projectService.getByID(projectID).map(p -> new APIResponse<>(ProjectResponse.builder()
                        .projectID(p.getId().toString())
                        .name(p.getName())
                        .description(p.getDescription())
                        .type(p.getType())
                        .build())).orElse(new APIResponse<>(ProjectResponse.builder().build()));
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectResponse implements Serializable
    {
        private String projectID;
        private String name;
        private String description;
        private ProjectType.Type type;
    }
}
