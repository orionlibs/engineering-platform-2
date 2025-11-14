package com.dimi.project.project.member;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.project.member.ProjectMemberModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class AddUserToProjectResult extends ServiceOperationResult
{
    private ProjectMemberModel projectMember;
}
