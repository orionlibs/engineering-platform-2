package com.dimi.user.api.authority;

import com.dimi.core.api.APIResponse;
import com.dimi.user.authority.UnassignAuthorityToUserResult;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.authority.request.UnassignAuthorityFromUserRequest;
import com.dimi.user.user.UserError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UnassignAuthorityToUserAPI
{
    @Autowired UserAuthorityService userAuthorityService;


    @DeleteMapping(value = "/users/authority-assignments")
    public ResponseEntity<APIResponse> unassignAuthorityFromUser(@Valid @RequestBody UnassignAuthorityFromUserRequest request)
    {
        UnassignAuthorityToUserResult result = userAuthorityService.unassignAuthority(request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserError.USER_NOT_FOUND)
                            || result.getError().getErrorCode().equals(UserError.USER_AUTHORITY_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
