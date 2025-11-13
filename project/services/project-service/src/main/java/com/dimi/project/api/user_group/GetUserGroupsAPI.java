package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.user_group.UserGroupService;
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
public class GetUserGroupsAPI
{
    @Autowired private UserGroupService userGroupService;


    @GetMapping(value = "/projects/user-groups")
    public ResponseEntity<APIResponse> getUserGroups()
    {
        List<UserGroup> userGroups = new ArrayList<>();
        userGroupService.getAll().forEach(g -> userGroups.add(UserGroup.builder()
                        .id(g.getId().toString())
                        .name(g.getName())
                        .description(g.getDescription())
                        .build()));
        APIResponse<UserGroupsResponse> response = new APIResponse<>(UserGroupsResponse.builder()
                        .userGroups(userGroups)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class UserGroupsResponse implements Serializable
    {
        private List<UserGroup> userGroups;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class UserGroup implements Serializable
    {
        private String id;
        private String name;
        private String description;
    }
}
