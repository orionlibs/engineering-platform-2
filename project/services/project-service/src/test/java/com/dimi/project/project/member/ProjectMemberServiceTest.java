package com.dimi.project.project.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.project.TestBase;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType.Type;
import com.dimi.project.model.project.member.ProjectMemberModel;
import com.dimi.project.project.ProjectService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProjectMemberServiceTest extends TestBase
{
    @Autowired ProjectService projectService;
    @Autowired ProjectMemberService projectMemberService;


    @BeforeEach
    void setup()
    {
        projectService.deleteAll();
        projectMemberService.deleteAll();
    }


    @Test
    void addUserToProject()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        UUID userID = UUID.randomUUID();
        AddUserToProjectResult member = projectMemberService.addUserToProject(proj.getId(), userID);
        Optional<ProjectMemberModel> wrap = projectMemberService.getByID(member.getProjectMember().getId());
        assertThat(wrap.isPresent()).isTrue();
        ProjectMemberModel member2 = wrap.get();
        assertThat(member2).isNotNull();
        assertThat(member2.getProject().getName()).isEqualTo("project1");
        assertThat(member2.getProject().getDescription()).isEqualTo("description1");
    }


    @Test
    void removeUserFromProject()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        UUID userID = UUID.randomUUID();
        AddUserToProjectResult member = projectMemberService.addUserToProject(proj.getId(), userID);
        Optional<ProjectMemberModel> wrap = projectMemberService.getByID(member.getProjectMember().getId());
        assertThat(wrap.isPresent()).isTrue();
        projectMemberService.removeUserFromProject(proj.getId(), userID);
        wrap = projectMemberService.getByID(member.getProjectMember().getId());
        assertThat(wrap.isEmpty()).isTrue();
    }


    @Test
    void getProjectsThatUserIsMemberOf()
    {
        ProjectModel proj1 = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        ProjectModel proj2 = saveProject("project2", "PR2", Type.ENGINEERING, "description2", "https://some.com/image.jpg");
        UUID userID = UUID.randomUUID();
        projectMemberService.addUserToProject(proj1.getId(), userID);
        projectMemberService.addUserToProject(proj2.getId(), userID);
        List<ProjectMemberModel> projects = projectMemberService.getProjectsThatUserIsMemberOf(userID);
        assertThat(projects.size()).isEqualTo(2);
    }
}
