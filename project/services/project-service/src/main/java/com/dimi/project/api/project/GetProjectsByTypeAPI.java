package com.dimi.project.api.project;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.ProjectService;
import com.dimi.project.model.project.ProjectType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectsByTypeAPI
{
    @Autowired ProjectService projectService;


    @GetMapping(value = "/projects")
    public ResponseEntity<APIResponse> getProjectsByType(@RequestParam(name = "type") ProjectType.Type type)
    {
        List<String> projectIDs = new ArrayList<>();
        projectService.getByType(type).forEach(p -> projectIDs.add(p.getId().toString()));
        APIResponse<ProjectsByTypeResponse> response = new APIResponse<>(ProjectsByTypeResponse.builder()
                        .projectIDs(projectIDs)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectsByTypeResponse implements Serializable
    {
        private List<String> projectIDs;
    }
}
