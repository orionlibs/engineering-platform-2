package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.user_group.UserGroupService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class DeleteUserGroupAPI
{
    @Autowired UserGroupService userGroupService;


    @DeleteMapping(value = "/projects/user-groups/{userGroupID}")
    public ResponseEntity<APIResponse> deleteUserGroup(@PathVariable(name = "userGroupID") UUID userGroupID)
    {
        userGroupService.delete(userGroupID);
        return ResponseEntity.ok(new APIResponse());
    }
}
