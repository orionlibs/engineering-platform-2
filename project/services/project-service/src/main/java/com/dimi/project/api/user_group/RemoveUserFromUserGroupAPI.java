package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.user_group.RemoveUserFromUserGroupResult;
import com.dimi.project.user_group.UserGroupError;
import com.dimi.project.user_group.UserGroupService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class RemoveUserFromUserGroupAPI
{
    @Autowired private UserGroupService userGroupService;


    @DeleteMapping(value = "/projects/user-groups/{userGroupID}/assignees/{userID}")
    public ResponseEntity<APIResponse> removeUserFromUserGroup(@PathVariable(name = "userGroupID") UUID userGroupID, @PathVariable(name = "userID") UUID userID)
    {
        RemoveUserFromUserGroupResult result = userGroupService.removeUserFromUserGroup(userGroupID, userID);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserGroupError.USER_GROUP_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
