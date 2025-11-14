package com.dimi.project.project.group.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.project.TestBase;
import com.dimi.project.api.project.group.CreateProjectGroupAPI.NewProjectGroupRequest;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType.Type;
import com.dimi.project.model.project.group.ProjectGroupMemberModel;
import com.dimi.project.project.group.CreateProjectGroupResult;
import com.dimi.project.project.group.ProjectGroupService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProjectGroupMemberServiceTest extends TestBase
{
    @Autowired ProjectGroupService projectGroupService;
    @Autowired ProjectGroupMemberService projectGroupMemberService;


    @BeforeEach
    void setup()
    {
        projectService.deleteAll();
        projectGroupService.deleteAll();
        projectGroupMemberService.deleteAll();
    }


    @Test
    void addProjectToGroup()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        CreateProjectGroupResult group = projectGroupService.createProjectGroup(NewProjectGroupRequest.builder()
                        .name("New Project Group")
                        .description("Description")
                        .build());
        AddProjectToGroupResult member = projectGroupMemberService.addProjectToGroup(proj.getId(), group.getProjectGroup().getId());
        Optional<ProjectGroupMemberModel> wrap = projectGroupMemberService.getByID(member.getProjectGroupMember().getId());
        assertThat(wrap.isPresent()).isTrue();
        ProjectGroupMemberModel member2 = wrap.get();
        assertThat(member2).isNotNull();
        assertThat(member2.getProject().getName()).isEqualTo("project1");
        assertThat(member2.getProject().getDescription()).isEqualTo("description1");
        assertThat(member2.getProjectGroup().getName()).isEqualTo("New Project Group");
        assertThat(member2.getProjectGroup().getDescription()).isEqualTo("Description");
    }


    @Test
    void removeProjectFromGroup()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        CreateProjectGroupResult group = projectGroupService.createProjectGroup(NewProjectGroupRequest.builder()
                        .name("New Project Group")
                        .description("Description")
                        .build());
        AddProjectToGroupResult member = projectGroupMemberService.addProjectToGroup(proj.getId(), group.getProjectGroup().getId());
        Optional<ProjectGroupMemberModel> wrap = projectGroupMemberService.getByID(member.getProjectGroupMember().getId());
        assertThat(wrap.isPresent()).isTrue();
        projectGroupMemberService.removeProjectFromGroup(proj.getId(), group.getProjectGroup().getId());
        wrap = projectGroupMemberService.getByID(member.getProjectGroupMember().getId());
        assertThat(wrap.isEmpty()).isTrue();
    }


    @Test
    void getProjectsByGroupID()
    {
        ProjectModel proj1 = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        ProjectModel proj2 = saveProject("project2", "PR2", Type.ENGINEERING, "description2", "https://some.com/image.jpg");
        CreateProjectGroupResult group = projectGroupService.createProjectGroup(NewProjectGroupRequest.builder()
                        .name("New Project Group")
                        .description("Description")
                        .build());
        projectGroupMemberService.addProjectToGroup(proj1.getId(), group.getProjectGroup().getId());
        projectGroupMemberService.addProjectToGroup(proj2.getId(), group.getProjectGroup().getId());
        List<ProjectGroupMemberModel> projects = projectGroupMemberService.getProjectsByGroupID(group.getProjectGroup().getId());
        assertThat(projects.size()).isEqualTo(2);
    }
}
