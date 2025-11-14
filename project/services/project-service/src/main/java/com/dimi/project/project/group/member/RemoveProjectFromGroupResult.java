package com.dimi.project.project.group.member;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.project.group.ProjectGroupMemberModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class RemoveProjectFromGroupResult extends ServiceOperationResult
{
    private ProjectGroupMemberModel projectGroupMember;
}
