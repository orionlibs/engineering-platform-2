package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.PermissionService;
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
public class GetProjectPermissionsAPI
{
    @Autowired PermissionService permissionService;


    @GetMapping(value = "/projects/permissions")
    public ResponseEntity<APIResponse> getProjectPermissions()
    {
        List<ProjectPermission> permissions = new ArrayList<>();
        permissionService.getAll().forEach(p -> permissions.add(ProjectPermission.builder()
                        .id(p.getId().toString())
                        .name(p.getName())
                        .description(p.getDescription())
                        .build()));
        APIResponse<ProjectPermissionsResponse> response = new APIResponse<>(ProjectPermissionsResponse.builder()
                        .permissions(permissions)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectPermissionsResponse implements Serializable
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
