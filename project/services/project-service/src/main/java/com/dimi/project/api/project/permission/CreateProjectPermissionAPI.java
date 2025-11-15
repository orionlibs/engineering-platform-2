package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.CreatePermissionResult;
import com.dimi.project.permission.PermissionService;
import com.dimi.project.permission.request.CreateProjectPermissionRequest;
import jakarta.validation.Valid;
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
    @Autowired private PermissionService permissionService;


    @PostMapping(value = "/projects/permissions")
    public ResponseEntity<APIResponse> createProjectPermission(@Valid @RequestBody CreateProjectPermissionRequest request)
    {
        CreatePermissionResult result = permissionService.createPermission(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
