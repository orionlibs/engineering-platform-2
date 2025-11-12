package com.dimi.project.api.project;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.core.api.APIResponse;
import com.dimi.core.test.APITestUtils;
import com.dimi.project.project.ProjectService;
import com.dimi.project.TestBase;
import com.dimi.project.api.project.UpdateProjectAPI.UpdateProjectRequest;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType;
import com.dimi.project.model.project.ProjectType.Type;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UpdateProjectAPITest extends TestBase
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    HttpHeaders headers;
    @Autowired ProjectService projectService;


    @BeforeEach
    void setUp()
    {
        projectService.deleteAll();
        addPortToAPIEndpoint(port);
        headers = new HttpHeaders();
        RestAssured.baseURI = apiEndpointPrefix;
    }


    @Test
    void updateProject_success()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        RestAssured.baseURI += "/projects/" + proj.getId();
        UpdateProjectRequest request = UpdateProjectRequest.builder()
                        .name("Test Project")
                        .code("Test Code")
                        .description("Test Description")
                        .type(Type.OTHER)
                        .build();
        Response response = apiUtils.makePutAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(200);
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


    @Test
    void updateProject_invalidInputs()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        RestAssured.baseURI += "/projects/" + proj.getId();
        UpdateProjectRequest request = UpdateProjectRequest.builder().build();
        Response response = apiUtils.makePutAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(400);
        APIResponse body = response.as(APIResponse.class);
        assertThat(body.getError().getErrorMessage()).isEqualTo("Validation failed for one or more fields");
    }
}
