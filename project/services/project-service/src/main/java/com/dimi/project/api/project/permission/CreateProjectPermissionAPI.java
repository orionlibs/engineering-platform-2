package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.CreatePermissionResult;
import com.dimi.project.permission.PermissionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateProjectPermissionAPI
{
    @Autowired PermissionService permissionService;


    @PostMapping(value = "/projects/permissions")
    public ResponseEntity<APIResponse> createProjectPermission(@Valid @RequestBody NewPermissionRequest request)
    {
        CreatePermissionResult result = permissionService.createPermission(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class NewPermissionRequest implements Serializable
    {
        @NotBlank(message = "name must not be blank")
        private String name;
        private String description;
    }
}
