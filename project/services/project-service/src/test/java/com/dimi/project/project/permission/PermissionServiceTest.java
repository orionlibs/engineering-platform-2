package com.dimi.project.project.permission;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.project.TestBase;
import com.dimi.project.model.project.permission.PermissionAssignedToUserModel;
import com.dimi.project.model.project.permission.PermissionModel;
import com.dimi.project.model.project.permission.PermissionsAssignedToUsersDAO;
import com.dimi.project.permission.AssignPermissionToUserGroupResult;
import com.dimi.project.permission.AssignPermissionToUserResult;
import com.dimi.project.permission.CreatePermissionResult;
import com.dimi.project.permission.PermissionService;
import com.dimi.project.permission.UnassignPermissionToUserGroupResult;
import com.dimi.project.permission.request.CreateProjectPermissionRequest;
import com.dimi.project.user_group.AddUserToUserGroupResult;
import com.dimi.project.user_group.CreateUserGroupResult;
import com.dimi.project.user_group.UserGroupService;
import com.dimi.project.user_group.request.AddUserToUserGroupRequest;
import com.dimi.project.user_group.request.CreateUserGroupRequest;
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
    @Autowired UserGroupService userGroupService;


    @BeforeEach
    void setup()
    {
        permissionService.deleteAll();
        userGroupService.deleteAll();
        permissionsAssignedToUsersDAO.deleteAll();
    }


    @Test
    void createPermission()
    {
        CreatePermissionResult permission = permissionService.createPermission(CreateProjectPermissionRequest.builder()
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
        CreatePermissionResult permission1 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreatePermissionResult permission2 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission2")
                        .description("description2")
                        .build());
        UUID userID = UUID.randomUUID();
        AssignPermissionToUserResult assignment1 = permissionService.assignPermissionToUser(permission1.getPermission().getId(), userID);
        AssignPermissionToUserResult assignment2 = permissionService.assignPermissionToUser(permission2.getPermission().getId(), userID);
        Optional<PermissionAssignedToUserModel> result1 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission1.getPermission(), userID);
        assertThat(result1.get().getPermission().getName()).isEqualTo(permission1.getPermission().getName());
        Optional<PermissionAssignedToUserModel> result2 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission2.getPermission(), userID);
        assertThat(result2.get().getPermission().getName()).isEqualTo(permission2.getPermission().getName());
        permissionService.deleteAll();
        result1 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission1.getPermission(), userID);
        assertThat(result1.isEmpty()).isTrue();
    }


    @Test
    void assignPermissionToUserGroup()
    {
        CreatePermissionResult permission1 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreateUserGroupResult userGroup = userGroupService.createGroup(CreateUserGroupRequest.builder()
                        .name("group1")
                        .description("description1")
                        .build());
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        AddUserToUserGroupResult groupAssignment1 = userGroupService.addUserToUserGroup(userGroup.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID1)
                        .build());
        AddUserToUserGroupResult groupAssignment2 = userGroupService.addUserToUserGroup(userGroup.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID2)
                        .build());
        AssignPermissionToUserGroupResult assignment1 = permissionService.assignPermissionToUserGroup(permission1.getPermission().getId(), userGroup.getUserGroup().getId());
        List<PermissionModel> result1 = permissionService.getAllPermissionsAssignedToUserGroup(userGroup.getUserGroup().getId());
        assertThat(result1.size()).isEqualTo(2);
    }


    @Test
    void unassignPermissionFromUser()
    {
        CreatePermissionResult permission1 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreatePermissionResult permission2 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission2")
                        .description("description2")
                        .build());
        UUID userID = UUID.randomUUID();
        AssignPermissionToUserResult assignment1 = permissionService.assignPermissionToUser(permission1.getPermission().getId(), userID);
        AssignPermissionToUserResult assignment2 = permissionService.assignPermissionToUser(permission2.getPermission().getId(), userID);
        permissionService.unassignPermissionFromUser(permission1.getPermission().getId(), userID);
        Optional<PermissionAssignedToUserModel> result1 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission1.getPermission(), userID);
        assertThat(result1.isEmpty()).isTrue();
        Optional<PermissionAssignedToUserModel> result2 = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission2.getPermission(), userID);
        assertThat(result2.get().getPermission().getName()).isEqualTo(permission2.getPermission().getName());
    }


    @Test
    void unassignPermissionFromUserGroup()
    {
        CreatePermissionResult permission1 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreateUserGroupResult userGroup = userGroupService.createGroup(CreateUserGroupRequest.builder()
                        .name("group1")
                        .description("description1")
                        .build());
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        AddUserToUserGroupResult groupAssignment1 = userGroupService.addUserToUserGroup(userGroup.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID1)
                        .build());
        AddUserToUserGroupResult groupAssignment2 = userGroupService.addUserToUserGroup(userGroup.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID2)
                        .build());
        AssignPermissionToUserGroupResult assignment1 = permissionService.assignPermissionToUserGroup(permission1.getPermission().getId(), userGroup.getUserGroup().getId());
        List<PermissionModel> result1 = permissionService.getAllPermissionsAssignedToUserGroup(userGroup.getUserGroup().getId());
        assertThat(result1.size()).isEqualTo(2);
        UnassignPermissionToUserGroupResult result2 = permissionService.unassignPermissionFromUserGroup(permission1.getPermission().getId(), userGroup.getUserGroup().getId());
        List<PermissionModel> result3 = permissionService.getAllPermissionsAssignedToUserGroup(userGroup.getUserGroup().getId());
        assertThat(result3.isEmpty()).isTrue();
    }


    @Test
    void getAllPermissionsAssignedToUser()
    {
        CreatePermissionResult permission1 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission1")
                        .description("description1")
                        .build());
        CreatePermissionResult permission2 = permissionService.createPermission(CreateProjectPermissionRequest.builder()
                        .name("permission2")
                        .description("description2")
                        .build());
        UUID userID = UUID.randomUUID();
        AssignPermissionToUserResult assignment1 = permissionService.assignPermissionToUser(permission1.getPermission().getId(), userID);
        AssignPermissionToUserResult assignment2 = permissionService.assignPermissionToUser(permission2.getPermission().getId(), userID);
        List<PermissionAssignedToUserModel> result1 = permissionService.getAllPermissionsAssignedToUser(userID);
        assertThat(result1.size()).isEqualTo(2);
    }
}
