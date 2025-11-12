package com.dimi.project.api.user_group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.user_group.CreateUserGroupResult;
import com.dimi.project.user_group.UserGroupError;
import com.dimi.project.user_group.UserGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateUserGroupAPI
{
    @Autowired UserGroupService userGroupService;


    @PostMapping(value = "/projects/user-groups")
    public ResponseEntity<APIResponse> createUserGroup(@Valid @RequestBody NewUserGroupRequest request)
    {
        CreateUserGroupResult result = userGroupService.createGroup(request);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(UserGroupError.USER_GROUP_ALREADY_EXISTS))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class NewUserGroupRequest implements Serializable
    {
        @NotBlank(message = "name must not be blank")
        private String name;
        private String description;
    }
}
