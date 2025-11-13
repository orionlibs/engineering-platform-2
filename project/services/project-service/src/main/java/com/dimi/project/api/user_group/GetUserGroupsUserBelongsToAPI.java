package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.model.user_group.UserInUserGroupModel;
import com.dimi.project.user_group.UserGroupService;
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
public class GetUserGroupsUserBelongsToAPI
{
    @Autowired private UserGroupService userGroupService;


    @GetMapping(value = "/projects/user-groups/assignees/{userID}")
    public ResponseEntity<APIResponse> getUserGroupsUserBelongsTo(@PathVariable(name = "userID") UUID userID)
    {
        List<UserInUserGroupModel> groupsFound = userGroupService.getAllUserGroupsUserBelongsTo(userID);
        List<UserGroupResponse> groups = new ArrayList<>();
        for(UserInUserGroupModel group : groupsFound)
        {
            groups.add(UserGroupResponse.builder()
                            .userGroupID(group.getId().toString())
                            .name(group.getUserGroup().getName())
                            .description(group.getUserGroup().getDescription())
                            .build());
        }
        APIResponse<UserGroupsResponse> response = new APIResponse<>(UserGroupsResponse.builder()
                        .userGroups(groups)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class UserGroupsResponse implements Serializable
    {
        private List<UserGroupResponse> userGroups;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class UserGroupResponse implements Serializable
    {
        private String userGroupID;
        private String name;
        private String description;
    }
}
