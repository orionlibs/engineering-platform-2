package com.dimi.project.user_group;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.user_group.UserInUserGroupModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class AddUserToUserGroupResult extends ServiceOperationResult
{
    private UserInUserGroupModel userInUserGroup;
}
