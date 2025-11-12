package com.dimi.project.api.project;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.core.api.APIResponse;
import com.dimi.core.test.APITestUtils;
import com.dimi.project.project.ProjectService;
import com.dimi.project.TestBase;
import com.dimi.project.api.project.UpdateProjectAvatarAPI.ProjectAvatarRequest;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType.Type;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UpdateProjectAvatarAPITest extends TestBase
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    HttpHeaders headers;
    @Autowired ProjectService projectService;


    @BeforeEach
    void setUp()
    {
        addPortToAPIEndpoint(port);
        headers = new HttpHeaders();
        RestAssured.baseURI = apiEndpointPrefix;
        projectService.deleteAll();
    }


    @Test
    void updateProjectAvatar_success()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        RestAssured.baseURI += "/projects/" + proj.getId() + "/avatars";
        ProjectAvatarRequest request = ProjectAvatarRequest.builder()
                        .avatarURL("https://some.com/image.jpg")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void updateProjectAvatar_invalidInputs()
    {
        ProjectModel proj = saveProject("project1", "PR1", Type.ENGINEERING, "description1", "https://some.com/image.jpg");
        RestAssured.baseURI += "/projects/" + proj.getId() + "/avatars";
        ProjectAvatarRequest request = ProjectAvatarRequest.builder().build();
        Response response = apiUtils.makePatchAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(400);
        APIResponse body = response.as(APIResponse.class);
        assertThat(body.getError().getErrorMessage()).isEqualTo("Validation failed for one or more fields");
    }
}
