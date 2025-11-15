package com.dimi.user.authority;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.user.TestBase;
import com.dimi.user.authority.request.CreateUserAuthorityRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserAuthorityServiceTest extends TestBase
{
    @Autowired UserAuthorityService userAuthorityService;


    @BeforeEach
    void setup()
    {
        userAuthorityService.deleteAll();
    }


    @Test
    void createAuthority()
    {
        CreateAuthorityResult authority = userAuthorityService.createAuthority(CreateUserAuthorityRequest.builder()
                        .authority(UserAuthority.USER.name())
                        .build());
        assertThat(authority.getAuthority()).isNotNull();
        assertThat(authority.getAuthority().getAuthority()).isEqualTo(UserAuthority.USER.name());
        assertThat(authority.getAuthority().getCreatedAt().toString()).isNotBlank();
        assertThat(authority.getAuthority().getUpdatedAt().toString()).isNotBlank();
    }
}
