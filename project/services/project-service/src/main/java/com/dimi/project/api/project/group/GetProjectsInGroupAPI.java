package com.dimi.project.api.project.group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.group.member.ProjectGroupMemberService;
import com.dimi.project.model.project.group.ProjectGroupMemberModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class GetProjectsInGroupAPI
{
    @Autowired ProjectGroupMemberService projectGroupMemberService;


    @GetMapping(value = "/projects/groups/{projectGroupID}")
    public ResponseEntity<APIResponse> getProjectsInGroup(@PathVariable(name = "projectGroupID") UUID projectGroupID)
    {
        List<ProjectGroupMemberModel> projectsFound = projectGroupMemberService.getProjectsByGroupID(projectGroupID);
        List<String> projectIDs = new ArrayList<>();
        projectsFound.forEach(p -> projectIDs.add(p.getId().toString()));
        APIResponse<ProjectsResponse> response = new APIResponse<>(ProjectsResponse.builder()
                        .projectIDs(projectIDs)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectsResponse implements Serializable
    {
        private List<String> projectIDs;
    }
}
