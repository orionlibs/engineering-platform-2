package com.dimi.user.user;

import com.dimi.core.ServiceOperationResult;
import com.dimi.user.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class RevokePermissionForUserResult extends ServiceOperationResult
{
    private UserModel user;
}
