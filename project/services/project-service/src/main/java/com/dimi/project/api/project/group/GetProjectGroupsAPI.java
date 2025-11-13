package com.dimi.project.api.project.group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.project.group.ProjectGroupService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectGroupsAPI
{
    @Autowired private ProjectGroupService projectGroupService;


    @GetMapping(value = "/projects/groups")
    public ResponseEntity<APIResponse> getProjectGroups()
    {
        List<ProjectGroupModel> groupsFound = projectGroupService.getAll();
        List<ProjectGroupResponse> groups = new ArrayList<>();
        for(ProjectGroupModel group : groupsFound)
        {
            groups.add(ProjectGroupResponse.builder()
                            .projectGroupID(group.getId().toString())
                            .name(group.getName())
                            .description(group.getDescription())
                            .build());
        }
        APIResponse<ProjectGroupsResponse> response = new APIResponse<>(ProjectGroupsResponse.builder()
                        .projectGroups(groups)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectGroupsResponse implements Serializable
    {
        private List<ProjectGroupResponse> projectGroups;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectGroupResponse implements Serializable
    {
        private String projectGroupID;
        private String name;
        private String description;
    }
}
