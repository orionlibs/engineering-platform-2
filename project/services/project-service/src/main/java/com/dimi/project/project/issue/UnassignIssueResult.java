package com.dimi.project.project.issue;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.project.issue.IssueAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class UnassignIssueResult extends ServiceOperationResult
{
    private IssueAssignmentModel issueAssignment;
}
