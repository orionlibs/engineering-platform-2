package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.user_group.CreateUserGroupResult;
import com.dimi.project.user_group.UserGroupService;
import com.dimi.project.user_group.request.CreateUserGroupRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateUserGroupAPI
{
    @Autowired private UserGroupService userGroupService;


    @PostMapping(value = "/projects/user-groups")
    public ResponseEntity<APIResponse> createUserGroup(@Valid @RequestBody CreateUserGroupRequest request)
    {
        CreateUserGroupResult result = userGroupService.createGroup(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
