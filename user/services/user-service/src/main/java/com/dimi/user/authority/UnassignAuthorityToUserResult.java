package com.dimi.user.authority;

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
public class UnassignAuthorityToUserResult extends ServiceOperationResult
{
    private UserModel user;
}
