package com.dimi.project.api.project;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.core.api.APIResponse;
import com.dimi.core.test.APITestUtils;
import com.dimi.project.TestBase;
import com.dimi.project.api.project.GetProjectTypesAPI.ProjectTypesResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GetProjectTypesAPITest extends TestBase
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
    void getProjectTypes()
    {
        RestAssured.baseURI += "/projects/types";
        Response response = apiUtils.makeGetAPICall(headers);
        assertThat(response.statusCode()).isEqualTo(200);
        APIResponse<ProjectTypesResponse> body = response.as(new TypeRef<APIResponse<ProjectTypesResponse>>()
        {
        });
        assertThat(body.getData().getProjectTypes()).containsExactlyInAnyOrderElementsOf(List.of("Unknown", "Engineering", "Software", "Other"));
    }
}
