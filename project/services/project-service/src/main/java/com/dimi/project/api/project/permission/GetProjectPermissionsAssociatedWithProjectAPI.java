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
public class GetProjectPermissionsAssociatedWithProjectAPI
{
    @Autowired PermissionService permissionService;


    @GetMapping(value = "/projects/{projectID}/permissions/associations")
    public ResponseEntity<APIResponse> getProjectPermissionsAssociatedWithProject(@PathVariable(name = "projectID") UUID projectID)
    {
        List<ProjectPermission> permissions = new ArrayList<>();
        permissionService.getAllPermissionsAssociatedWithProject(projectID).forEach(p -> permissions.add(ProjectPermission.builder()
                        .id(p.getPermission().getId().toString())
                        .name(p.getPermission().getName())
                        .description(p.getPermission().getDescription())
                        .build()));
        APIResponse<ProjectPermissionsAssociatedWithProjectResponse> response = new APIResponse<>(ProjectPermissionsAssociatedWithProjectResponse.builder()
                        .permissions(permissions)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectPermissionsAssociatedWithProjectResponse implements Serializable
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
