package com.dimi.user.permission;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.user.TestBase;
import com.dimi.user.api.permission.CreateUserPermissionAPI.NewUserPermissionRequest;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserPermissionServiceTest extends TestBase
{
    @Autowired UserService userService;
    @Autowired UserAuthorityService userAuthorityService;
    @Autowired UserPermissionService userPermissionService;
    @Autowired UserPermissionsPerAuthorityService userPermissionsPerAuthorityService;


    @BeforeEach
    void setup()
    {
        userService.deleteAll();
        userAuthorityService.deleteAll();
        userPermissionService.deleteAll();
        userPermissionsPerAuthorityService.deleteAll();
    }


    @Test
    void createPermission()
    {
        CreatePermissionResult permission = userPermissionService.createPermission(NewUserPermissionRequest.builder()
                        .name("perm1")
                        .description("descr1")
                        .build());
        assertThat(permission.getPermission()).isNotNull();
        assertThat(permission.getPermission().getName()).isEqualTo("perm1");
        assertThat(permission.getPermission().getDescription()).isEqualTo("descr1");
        assertThat(permission.getPermission().getCreatedAt().toString()).isNotBlank();
        assertThat(permission.getPermission().getUpdatedAt().toString()).isNotBlank();
    }
}
