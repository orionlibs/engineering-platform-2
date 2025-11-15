package com.dimi.project.project.group;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.project.TestBase;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.group.request.CreateProjectGroupRequest;
import com.dimi.project.project.group.request.UpdateProjectGroupRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProjectGroupServiceTest extends TestBase
{
    @Autowired ProjectService projectService;
    @Autowired ProjectGroupService projectGroupService;


    @BeforeEach
    void setup()
    {
        projectService.deleteAll();
        projectGroupService.deleteAll();
    }


    @Test
    void createProjectGroup()
    {
        CreateProjectGroupResult group = projectGroupService.createProjectGroup(CreateProjectGroupRequest.builder()
                        .name("New Project Group")
                        .description("Description")
                        .build());
        List<ProjectGroupModel> groups = projectGroupService.getAll();
        assertThat(groups).isNotNull();
        assertThat(groups.size()).isEqualTo(1);
        assertThat(groups.get(0).getName()).isEqualTo("New Project Group");
        assertThat(groups.get(0).getDescription()).isEqualTo("Description");
        assertThat(groups.get(0).getCreatedAt().toString()).isNotBlank();
        assertThat(groups.get(0).getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void updateProjectGroup()
    {
        CreateProjectGroupResult group1 = projectGroupService.createProjectGroup(CreateProjectGroupRequest.builder()
                        .name("New Project Group")
                        .description("Description")
                        .build());
        projectGroupService.updateProjectGroup(group1.getProjectGroup().getId(), UpdateProjectGroupRequest.builder()
                        .name("New Project Group 2")
                        .description("Description 2")
                        .build());
        List<ProjectGroupModel> groups = projectGroupService.getAll();
        assertThat(groups).isNotNull();
        assertThat(groups.size()).isEqualTo(1);
        assertThat(groups.get(0).getName()).isEqualTo("New Project Group 2");
        assertThat(groups.get(0).getDescription()).isEqualTo("Description 2");
        assertThat(groups.get(0).getCreatedAt().toString()).isNotBlank();
        assertThat(groups.get(0).getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void getByID()
    {
        CreateProjectGroupResult group1 = projectGroupService.createProjectGroup(CreateProjectGroupRequest.builder()
                        .name("New Project Group")
                        .description("Description")
                        .build());
        Optional<ProjectGroupModel> wrap = projectGroupService.getByID(group1.getProjectGroup().getId());
        // then
        ProjectGroupModel group = wrap.get();
        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo("New Project Group");
        assertThat(group.getDescription()).isEqualTo("Description");
        assertThat(group.getCreatedAt().toString()).isNotBlank();
        assertThat(group.getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void delete()
    {
        CreateProjectGroupResult group1 = projectGroupService.createProjectGroup(CreateProjectGroupRequest.builder()
                        .name("New Project Group")
                        .description("Description")
                        .build());
        projectService.delete(group1.getProjectGroup().getId());
        // when
        Optional<ProjectModel> wrap = projectService.getByID(group1.getProjectGroup().getId());
        // then
        assertThat(wrap.isEmpty()).isTrue();
    }
}
