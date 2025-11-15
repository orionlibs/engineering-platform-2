package com.dimi.project.project;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.project.TestBase;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType;
import com.dimi.project.model.project.ProjectType.Type;
import com.dimi.project.project.request.UpdateProjectRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProjectServiceTest extends TestBase
{
    @Autowired ProjectService projectService;


    @BeforeEach
    void setup()
    {
        projectService.deleteAll();
    }


    @Test
    void saveProjectsAndReturnByType()
    {
        // given
        ProjectModel proj1 = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        ProjectModel proj2 = saveProject("project2", "PR2", Type.OTHER, "description2", "https://some.com/image.jpg");
        ProjectModel proj3 = saveProject("project3", "PR3", ProjectType.Type.ENGINEERING, "description3", "https://some.com/image.jpg");
        // when
        List<ProjectModel> engineeringProjects = projectService.getByType(Type.ENGINEERING);
        List<ProjectModel> otherProjects = projectService.getByType(ProjectType.Type.OTHER);
        // then
        assertThat(engineeringProjects).isNotNull();
        assertThat(otherProjects).isNotNull();
        assertThat(engineeringProjects.size()).isEqualTo(2);
        assertThat(otherProjects.size()).isEqualTo(1);
        assertThat(engineeringProjects.get(0).getName()).isEqualTo("project1");
        assertThat(engineeringProjects.get(1).getName()).isEqualTo("project3");
        assertThat(engineeringProjects.get(0).getCode()).isEqualTo("PR1");
        assertThat(engineeringProjects.get(1).getCode()).isEqualTo("PR3");
        assertThat(otherProjects.get(0).getName()).isEqualTo("project2");
        assertThat(otherProjects.get(0).getCode()).isEqualTo("PR2");
        assertThat(engineeringProjects.get(0).getType()).isEqualTo(ProjectType.Type.ENGINEERING);
        assertThat(engineeringProjects.get(1).getType()).isEqualTo(ProjectType.Type.ENGINEERING);
        assertThat(otherProjects.get(0).getType()).isEqualTo(ProjectType.Type.OTHER);
        assertThat(engineeringProjects.get(0).getDescription()).isEqualTo("description1");
        assertThat(engineeringProjects.get(1).getDescription()).isEqualTo("description3");
        assertThat(otherProjects.get(0).getDescription()).isEqualTo("description2");
        assertThat(engineeringProjects.get(0).getAvatarURL()).isEqualTo("https://some.com/image.jpg");
        assertThat(engineeringProjects.get(1).getAvatarURL()).isEqualTo("https://some.com/image.jpg");
        assertThat(otherProjects.get(0).getAvatarURL()).isEqualTo("https://some.com/image.jpg");
        assertThat(engineeringProjects.get(0).getCreatedAt().toString()).isNotBlank();
        assertThat(engineeringProjects.get(1).getCreatedAt().toString()).isNotBlank();
        assertThat(otherProjects.get(0).getCreatedAt().toString()).isNotBlank();
        assertThat(engineeringProjects.get(0).getUpdatedAt().toString()).isNotBlank();
        assertThat(engineeringProjects.get(1).getUpdatedAt().toString()).isNotBlank();
        assertThat(otherProjects.get(0).getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void getByID()
    {
        // given
        ProjectModel proj1 = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        ProjectModel proj2 = saveProject("project2", "PR2", Type.OTHER, "description2", "https://some.com/image.jpg");
        ProjectModel proj3 = saveProject("project3", "PR3", ProjectType.Type.ENGINEERING, "description3", "https://some.com/image.jpg");
        // when
        Optional<ProjectModel> projWrap = projectService.getByID(proj2.getId());
        // then
        ProjectModel project = projWrap.get();
        assertThat(project).isNotNull();
        assertThat(project.getName()).isEqualTo("project2");
        assertThat(project.getCode()).isEqualTo("PR2");
        assertThat(project.getType()).isEqualTo(ProjectType.Type.OTHER);
        assertThat(project.getDescription()).isEqualTo("description2");
        assertThat(project.getAvatarURL()).isEqualTo("https://some.com/image.jpg");
        assertThat(project.getCreatedAt().toString()).isNotBlank();
        assertThat(project.getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void delete()
    {
        // given
        ProjectModel proj1 = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        ProjectModel proj2 = saveProject("project2", "PR2", Type.OTHER, "description2", "https://some.com/image.jpg");
        ProjectModel proj3 = saveProject("project3", "PR3", ProjectType.Type.ENGINEERING, "description3", "https://some.com/image.jpg");
        projectService.delete(proj2.getId());
        // when
        Optional<ProjectModel> projWrap = projectService.getByID(proj2.getId());
        // then
        assertThat(projWrap.isEmpty()).isTrue();
    }


    @Test
    void updateProject()
    {
        // given
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        projectService.updateProject(proj.getId(), UpdateProjectRequest.builder()
                        .name("Test Project")
                        .code("Test Code")
                        .description("Test Description")
                        .type(Type.OTHER)
                        .build());
        Optional<ProjectModel> projWrap = projectService.getByID(proj.getId());
        // then
        ProjectModel project = projWrap.get();
        assertThat(project).isNotNull();
        assertThat(project.getName()).isEqualTo("Test Project");
        assertThat(project.getCode()).isEqualTo("Test Code");
        assertThat(project.getType()).isEqualTo(ProjectType.Type.OTHER);
        assertThat(project.getDescription()).isEqualTo("Test Description");
        assertThat(project.getAvatarURL()).isEqualTo("https://some.com/image.jpg");
        assertThat(project.getCreatedAt().toString()).isNotBlank();
        assertThat(project.getUpdatedAt().toString()).isNotBlank();
    }
}
