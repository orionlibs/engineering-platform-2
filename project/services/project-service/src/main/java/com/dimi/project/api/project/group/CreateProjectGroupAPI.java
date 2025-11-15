package com.dimi.project.api.project.group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.group.CreateProjectGroupResult;
import com.dimi.project.project.group.ProjectGroupService;
import com.dimi.project.project.group.request.CreateProjectGroupRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateProjectGroupAPI
{
    @Autowired private ProjectGroupService projectGroupService;


    @PostMapping(value = "/projects/groups")
    public ResponseEntity<APIResponse> createProjectGroup(@Valid @RequestBody CreateProjectGroupRequest request)
    {
        CreateProjectGroupResult result = projectGroupService.createProjectGroup(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
