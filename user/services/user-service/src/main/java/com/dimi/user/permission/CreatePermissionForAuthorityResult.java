package com.dimi.user.permission;

import com.dimi.core.ServiceOperationResult;
import com.dimi.user.model.user.permission.UserPermissionsPerAuthorityModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreatePermissionForAuthorityResult extends ServiceOperationResult
{
    private UserPermissionsPerAuthorityModel permission;
}
