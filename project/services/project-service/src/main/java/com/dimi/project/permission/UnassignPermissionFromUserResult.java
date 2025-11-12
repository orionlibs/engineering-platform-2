package com.dimi.project.permission;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.project.permission.PermissionAssignedToUserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class UnassignPermissionFromUserResult extends ServiceOperationResult
{
    private PermissionAssignedToUserModel permission;
}
