package com.dimi.user.permission;

import com.dimi.core.ServiceOperationResult;
import com.dimi.user.model.permission.UserPermissionModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreatePermissionResult extends ServiceOperationResult
{
    private UserPermissionModel permission;
}
