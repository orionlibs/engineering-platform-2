package com.dimi.project.permission;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.project.permission.PermissionAssociatedWithProjectModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class DisassociatePermissionFromProjectResult extends ServiceOperationResult
{
    private PermissionAssociatedWithProjectModel permission;
}
