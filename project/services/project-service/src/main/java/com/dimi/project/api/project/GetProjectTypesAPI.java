package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.model.project.ProjectType;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectTypesAPI
{
    @GetMapping(value = "/projects/types")
    public ResponseEntity<APIResponse> getProjectTypes()
    {
        APIResponse<ProjectTypesResponse> response = new APIResponse<>(ProjectTypesResponse.builder()
                        .projectTypes(ProjectType.Type.getAllValues())
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectTypesResponse implements Serializable
    {
        private List<String> projectTypes;
    }
}
