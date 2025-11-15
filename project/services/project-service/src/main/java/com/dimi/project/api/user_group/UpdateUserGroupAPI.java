package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.user_group.UpdateUserGroupResult;
import com.dimi.project.user_group.UserGroupError;
import com.dimi.project.user_group.UserGroupService;
import com.dimi.project.user_group.request.UpdateUserGroupRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateUserGroupAPI
{
    @Autowired private UserGroupService userGroupService;


    @PutMapping(value = "/projects/user-groups/{userGroupID}")
    public ResponseEntity<APIResponse> updateUserGroup(@PathVariable(name = "userGroupID") UUID userGroupID, @Valid @RequestBody UpdateUserGroupRequest request)
    {
        UpdateUserGroupResult result = userGroupService.updateGroup(userGroupID, request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserGroupError.USER_GROUP_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
