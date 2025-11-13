package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.user_group.AddUserToUserGroupResult;
import com.dimi.project.user_group.UserGroupError;
import com.dimi.project.user_group.UserGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AddUserToUserGroupAPI
{
    @Autowired UserGroupService userGroupService;


    @PostMapping(value = "/projects/user-groups/{userGroupID}/assignees")
    public ResponseEntity<APIResponse> addUserToUserGroup(@PathVariable(name = "userGroupID") UUID userGroupID, @Valid @RequestBody AddUserToUserGroupRequest request)
    {
        AddUserToUserGroupResult result = userGroupService.addUserToUserGroup(userGroupID, request);
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


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class AddUserToUserGroupRequest implements Serializable
    {
        @NotNull(message = "userID must not be blank")
        private UUID userID;
    }
}
