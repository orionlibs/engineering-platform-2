package com.dimi.user.api.authority;

import com.dimi.core.api.APIResponse;
import com.dimi.user.authority.UserAuthority;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserAuthoritiesAPI
{
    @GetMapping(value = "/users/authorities")
    public ResponseEntity<APIResponse> getUserAuthorities()
    {
        APIResponse<UserAuthoritiesResponse> response = new APIResponse<>(UserAuthoritiesResponse.builder()
                        .authorities(UserAuthority.getAllValues())
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class UserAuthoritiesResponse implements Serializable
    {
        private List<String> authorities;
    }
}
