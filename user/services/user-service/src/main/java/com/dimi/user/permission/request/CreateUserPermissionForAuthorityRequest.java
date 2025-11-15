package com.dimi.user.permission.request;

import com.dimi.core.api.APIRequest;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreateUserPermissionForAuthorityRequest extends APIRequest
{
    @NotBlank(message = "name must not be blank")
    private UUID permissionID;
    private UUID authorityID;
}
