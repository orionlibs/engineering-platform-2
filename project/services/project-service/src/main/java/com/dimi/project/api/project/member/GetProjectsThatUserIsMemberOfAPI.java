package com.dimi.project.api.project.member;

import com.dimi.core.api.APIResponse;
import com.dimi.project.model.project.member.ProjectMemberModel;
import com.dimi.project.project.member.ProjectMemberService;
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
public class GetProjectsThatUserIsMemberOfAPI
{
    @Autowired private ProjectMemberService projectMemberService;


    @GetMapping(value = "/projects/members/{userID}")
    public ResponseEntity<APIResponse> getProjectsThatUserIsMemberOf(@PathVariable(name = "userID") UUID userID)
    {
        List<ProjectMemberModel> membershipsFound = projectMemberService.getProjectsThatUserIsMemberOf(userID);
        List<String> projectIDs = new ArrayList<>();
        membershipsFound.forEach(p -> projectIDs.add(p.getId().toString()));
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
