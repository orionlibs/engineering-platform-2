package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.PermissionService;
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
public class GetProjectPermissionsAssignedToUserGroupAPI
{
    @Autowired private PermissionService permissionService;


    @GetMapping(value = "/projects/permissions/assignee-groups/{userGroupID}")
    public ResponseEntity<APIResponse> getProjectPermissionsAssignedToUserGroup(@PathVariable(name = "userGroupID") UUID userGroupID)
    {
        List<ProjectPermission> permissions = new ArrayList<>();
        permissionService.getAllPermissionsAssignedToUserGroup(userGroupID).forEach(p -> permissions.add(ProjectPermission.builder()
                        .id(p.getId().toString())
                        .name(p.getName())
                        .description(p.getDescription())
                        .build()));
        APIResponse<ProjectPermissionsAssignedToUserResponse> response = new APIResponse<>(ProjectPermissionsAssignedToUserResponse.builder()
                        .permissions(permissions)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectPermissionsAssignedToUserResponse implements Serializable
    {
        private List<ProjectPermission> permissions;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectPermission implements Serializable
    {
        private String id;
        private String name;
        private String description;
    }
}
