package com.dimi.project.api.project;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.core.api.APIResponse;
import com.dimi.core.test.APITestUtils;
import com.dimi.project.TestBase;
import com.dimi.project.model.project.ProjectType.Type;
import com.dimi.project.project.request.CreateProjectRequest;
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
class CreateProjectAPITest extends TestBase
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    HttpHeaders headers;


    @BeforeEach
    void setUp()
    {
        addPortToAPIEndpoint(port);
        headers = new HttpHeaders();
        RestAssured.baseURI = apiEndpointPrefix;
    }


    @Test
    void createProject_success()
    {
        RestAssured.baseURI += "/projects";
        CreateProjectRequest request = CreateProjectRequest.builder()
                        .name("Test Project")
                        .code("Test Code")
                        .description("Test Description")
                        .type(Type.ENGINEERING)
                        .avatarURL("https://some.com/image.jpg")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(201);
    }


    @Test
    void createProject_invalidInputs()
    {
        RestAssured.baseURI += "/projects";
        CreateProjectRequest request = CreateProjectRequest.builder().build();
        Response response = apiUtils.makePostAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(400);
        APIResponse body = response.as(APIResponse.class);
        assertThat(body.getError().getErrorMessage()).isEqualTo("Validation failed for one or more fields");
    }
}
