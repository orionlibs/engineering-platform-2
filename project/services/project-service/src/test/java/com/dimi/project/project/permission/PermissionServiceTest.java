package com.dimi.project.project.permission;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.project.TestBase;
import com.dimi.project.api.project.permission.AssignProjectPermissionToUserAPI.AssignPermissionToUserRequest;
import com.dimi.project.api.project.permission.CreateProjectPermissionAPI.NewPermissionRequest;
import com.dimi.project.model.project.permission.PermissionAssignedToUserModel;
import com.dimi.project.model.project.permission.PermissionsAssignedToUsersDAO;
import com.dimi.project.permission.AssignPermissionToUserResult;
import com.dimi.project.permission.CreatePermissionResult;
import com.dimi.project.permission.PermissionService;
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
class PermissionServiceTest extends TestBase
{
    @Autowired PermissionsAssignedToUsersDAO permissionsAssignedToUsersDAO;
    @Autowired PermissionService permissionService;


    @BeforeEach
    void setup()
    {
        permissionService.deleteAll();
        permissionsAssignedToUsersDAO.deleteAll();
    }


    @Test
    void createPermission()
    {
        CreatePermissionResult permission = permissionService.createPermission(NewPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        assertThat(permission.getPermission()).isNotNull();
        assertThat(permission.getPermission().getName()).isEqualTo("permission1");
        assertThat(permission.getPermission().getDescription()).isEqualTo("description1");
    }


    @Test
    void assignPermissionToUser()
    {
        CreatePermissionResult permission1 = permissionService.createPermission(NewPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreatePermissionResult permission2 = permissionService.createPermission(NewPermissionRequest.builder()
                        .name("permission2")
                        .description("description2")
                        .build());
        UUID userID = UUID.randomUUID();
        AssignPermissionToUserResult assignment1 = permissionService.assignPermissionToUser(permission1.getPermission().getId(), AssignPermissionToUserRequest.builder()
                        .userID(userID)
                        .build());
        AssignPermissionToUserResult assignment2 = permissionService.assignPermissionToUser(permission2.getPermission().getId(), AssignPermissionToUserRequest.builder()
                        .userID(userID)
                        .build());
        Optional<PermissionAssignedToUserModel> result1 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission1.getPermission(), userID);
        assertThat(result1.get().getPermission().getName()).isEqualTo(permission1.getPermission().getName());
        Optional<PermissionAssignedToUserModel> result2 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission2.getPermission(), userID);
        assertThat(result2.get().getPermission().getName()).isEqualTo(permission2.getPermission().getName());
        permissionService.deleteAll();
        result1 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission1.getPermission(), userID);
        assertThat(result1.isEmpty()).isTrue();
    }


    @Test
    void unassignPermissionFromUser()
    {
        CreatePermissionResult permission1 = permissionService.createPermission(NewPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreatePermissionResult permission2 = permissionService.createPermission(NewPermissionRequest.builder()
                        .name("permission2")
                        .description("description2")
                        .build());
        UUID userID = UUID.randomUUID();
        AssignPermissionToUserResult assignment1 = permissionService.assignPermissionToUser(permission1.getPermission().getId(), AssignPermissionToUserRequest.builder()
                        .userID(userID)
                        .build());
        AssignPermissionToUserResult assignment2 = permissionService.assignPermissionToUser(permission2.getPermission().getId(), AssignPermissionToUserRequest.builder()
                        .userID(userID)
                        .build());
        permissionService.unassignPermissionFromUser(permission1.getPermission().getId(), userID);
        Optional<PermissionAssignedToUserModel> result1 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission1.getPermission(), userID);
        assertThat(result1.isEmpty()).isTrue();
        Optional<PermissionAssignedToUserModel> result2 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission2.getPermission(), userID);
        assertThat(result2.get().getPermission().getName()).isEqualTo(permission2.getPermission().getName());
    }


    @Test
    void getAllPermissionsAssignedToUser()
    {
        CreatePermissionResult permission1 = permissionService.createPermission(NewPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreatePermissionResult permission2 = permissionService.createPermission(NewPermissionRequest.builder()
                        .name("permission2")
                        .description("description2")
                        .build());
        UUID userID = UUID.randomUUID();
        AssignPermissionToUserResult assignment1 = permissionService.assignPermissionToUser(permission1.getPermission().getId(), AssignPermissionToUserRequest.builder()
                        .userID(userID)
                        .build());
        AssignPermissionToUserResult assignment2 = permissionService.assignPermissionToUser(permission2.getPermission().getId(), AssignPermissionToUserRequest.builder()
                        .userID(userID)
                        .build());
        List<PermissionAssignedToUserModel> result1 = permissionService.getAllPermissionsAssignedToUser(userID);
        assertThat(result1.size()).isEqualTo(2);
    }
}
