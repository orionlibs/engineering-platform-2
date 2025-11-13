package com.dimi.user.api.authority;

import com.dimi.core.api.APIResponse;
import com.dimi.user.authority.CreateAuthorityResult;
import com.dimi.user.authority.UserAuthorityService;
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
public class CreateUserAuthorityAPI
{
    @Autowired UserAuthorityService userAuthorityService;


    @PostMapping(value = "/users/authorities")
    public ResponseEntity<APIResponse> createUserAuthority(@Valid @RequestBody NewUserAuthorityRequest request)
    {
        CreateAuthorityResult result = userAuthorityService.createAuthority(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class NewUserAuthorityRequest implements Serializable
    {
        @NotBlank(message = "authority must not be blank")
        private String authority;
    }
}
