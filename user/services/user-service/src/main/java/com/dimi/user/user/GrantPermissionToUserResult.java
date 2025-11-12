package com.dimi.user.user;

import com.dimi.core.ServiceOperationResult;
import com.dimi.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class GrantPermissionToUserResult extends ServiceOperationResult
{
    private UserModel user;
}
